package com.wfw.study.loader;

import java.io.*;
import java.nio.file.Paths;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by killer9527 on 2018/3/21.
 * 自定义从硬盘加载类的类加载器
 * 一个ClassLoader创建时如果没有指定parent，那么它的parent默认就是AppClassLoader。
 */
public class DiskClassLoader extends ClassLoader {
    private String myLibPath;

    /**
     * 保存jar包中的.class文件的二进制字节
     */
    private Map<String, byte[]> clazzMap;

    public DiskClassLoader(String path) {
        this.myLibPath = path;
        this.clazzMap = new HashMap<>();
        readJarFile();
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String fileName = getFileName(name);
        try {
            //1. 首先从classes/*.class文件中直接读取
            File file = new File(Paths.get(this.myLibPath, "classes").toFile().getAbsolutePath(), fileName);
            FileInputStream fis = new FileInputStream(file);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int nextByteData;
            while ((nextByteData = fis.read()) != -1) {
                bos.write(nextByteData);
            }
            byte[] data = bos.toByteArray();

            fis.close();
            bos.close();

            //将class二进制内容转换成Class对象
            return defineClass(name, data, 0, data.length);
        } catch (FileNotFoundException e) {
            //2. 从jar包获取的.class文件中读取（已经存入缓存）
            if (this.clazzMap.containsKey(name)) {
                byte[] data = this.clazzMap.get(name);
                return defineClass(name, data, 0, data.length);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.findClass(name);
    }

    /**
     * 获取要加载的class文件名
     *
     * @param name
     * @return
     */
    private String getFileName(String name) {
        int index = name.lastIndexOf('.');
        if (index == -1) {
            return name + ".class";
        } else {
            return name.substring(index + 1) + ".class";
        }
    }

    /**
     * 读取jar包中的所有.class文件
     */
    private void readJarFile() {
        List<File> list = scanDir();
        for (File f : list) {
            JarFile jar;
            try {
                jar = new JarFile(f);
                readJAR(jar);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 扫描lib下面的所有jar包
     *
     * @return
     */
    private List<File> scanDir() {
        List<File> list = new ArrayList<>();
        File[] files = new File(Paths.get(this.myLibPath, "lib").toFile().getAbsolutePath()).listFiles();
        for (File f : files) {
            if (f.isFile() && f.getName().endsWith(".jar"))
                list.add(f);
        }
        return list;
    }

    /**
     * 读取一个jar包内的class文件，并存在当前加载器的map中
     *
     * @param jar
     * @throws IOException
     */
    private void readJAR(JarFile jar) throws IOException {
        Enumeration<JarEntry> en = jar.entries();
        while (en.hasMoreElements()) {
            JarEntry je = en.nextElement();
            String name = je.getName();
            if (name.endsWith(".class")) {
                String clazzName = name.replace(".class", "").replaceAll("/", ".");
                if (this.findLoadedClass(clazzName) != null) {
                    continue;
                }

                InputStream is = jar.getInputStream(je);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                int nextByteData;
                while ((nextByteData = is.read()) != -1) {
                    bos.write(nextByteData);
                }
                byte[] data = bos.toByteArray();
                is.close();
                bos.close();
                this.clazzMap.put(clazzName, data);//暂时保存下来
            }
        }
    }
}
