package com.wfw.study.ioc.annotation;

import java.io.File;
import java.lang.reflect.Field;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by killer9527 on 2018/3/27.
 * 基于注解的beanFactory
 */
public class MyAnnotationBeanFactory {
    private Map<String, Object> container = new HashMap<>();
    private String classPath = "";

    public MyAnnotationBeanFactory(String... basePackages) throws  ClassNotFoundException, InstantiationException,
            IllegalAccessException, FieldInjectionFailedException {
        this.classPath = MyAnnotationBeanFactory.class.getClassLoader().getResource("").getPath();
        //因为是在单元测试中测试的，所有路径需要替换一下
        this.classPath = this.classPath.replace("test-classes", "classes");
        String systemName = System.getProperty("os.name");
        if (systemName.startsWith("Win")) {
            this.classPath = this.classPath.substring(1);
        }
        //扫描所有basePackages下的.class文件到classFiles
        List<File> classFiles = new ArrayList<>();
        for (String basePackage : basePackages) {
            String packagePath = basePackage.replace(".", "/");
            File folder = Paths.get(this.classPath, packagePath).toFile();
            this.scanClass(folder, classFiles);
        }
        this.instantiateComponent(classFiles);
        this.injectField();
    }

    public Object getBean(String beanName){
        Object obj = this.container.get(beanName);
        return obj;
    }

    /**
     * 获取文件夹下所有的.class文件
     *
     * @param folder
     * @return
     */
    private void scanClass(File folder, List<File> list) {
        for (File f : folder.listFiles()) {
            if (f.isFile() && f.getName().endsWith(".class")) {
                list.add(f);
            } else if (!f.isFile()) {
                scanClass(f, list);
            }
        }
    }

    /**
     * 实例化被@Component注解的组件
     *
     * @param classFiles
     */
    private void instantiateComponent(List<File> classFiles) throws ClassNotFoundException, InstantiationException,
            IllegalAccessException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        for (File classFile : classFiles) {
            //获取类名并实例化
            String className = classFile.getAbsolutePath().substring(0, classFile.getAbsolutePath().length() - 6)
                    .replace("\\", "/")
                    .replace(this.classPath, "").replace("/", ".");
            Class<?> clazz = classLoader.loadClass(className);
            //如果该类被@MyComponent注解，则实例化并保存到container
            if (clazz.isAnnotationPresent(MyComponent.class)) {
                //获取@MyComponent注解及其value
                MyComponent myComponent = clazz.getAnnotation(MyComponent.class);
                String componentName = myComponent.value();
                if (componentName.equals("")) {
                    //@MyComponent的value为空时，使用类名作为id，类名首字母小写
                    componentName = clazz.getSimpleName();
                    componentName = componentName.substring(0, 1).toLowerCase() + componentName.substring(1);
                }
                if (!this.container.containsKey(componentName)){
                    this.container.put(componentName, clazz.newInstance());
                }
            }
        }
    }

    /**
     * 自动注入组件中依赖的由@MyResource注解注释的Field
     */
    private void injectField() throws FieldInjectionFailedException, IllegalAccessException{
        Iterator<Map.Entry<String, Object>> iterator = this.container.entrySet().iterator();
        //遍历所有被实例化的组件
        while (iterator.hasNext()){
            Map.Entry<String, Object> entry = iterator.next();
            Object component = entry.getValue();
            //找出所有被@MyResource注解注释的Field
            for(Field field : component.getClass().getDeclaredFields()){
                if (field.isAnnotationPresent(MyResource.class)){
                    //提取@MyResource中的name
                    MyResource myResource = field.getAnnotation(MyResource.class);
                    String myResourceName = myResource.name();
                    if (myResourceName.equals("")){
                        myResourceName = field.getName();
                    }
                    if (!this.container.containsKey(myResourceName)){
                        throw new FieldInjectionFailedException(component.getClass().getName() + "的" + field.getName() + "注入失败");
                    }
                    field.setAccessible(true);
                    field.set(component, this.container.get(myResourceName));
                }
            }
        }
    }
}
