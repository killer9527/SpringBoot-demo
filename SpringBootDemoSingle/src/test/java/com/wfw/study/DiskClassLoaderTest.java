package com.wfw.study;

import com.wfw.study.loader.DiskClassLoader;
import junit.framework.TestCase;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by killer9527 on 2018/3/21.
 */
public class DiskClassLoaderTest extends TestCase {
    public void testLoader() throws Exception {
        //测试加载路径为"F:\testLib"的类加载器
        DiskClassLoader firstDiskClassLoader = new DiskClassLoader("F:\\testLib");
        Class clazz = firstDiskClassLoader
                .loadClass("com.wfw.Speak");
        if (clazz != null) {
            Object object = clazz.newInstance();
            Method setMethod = clazz.getDeclaredMethod("say");
            setMethod.invoke(object);
        }

        //测试加载路径为"F:\testLib\ext"的类加载器
        DiskClassLoader extDistClassLoader = new DiskClassLoader("F:\\testLib\\ext");
        Class extClazz = extDistClassLoader.loadClass("com.wfw.Speak");
        if (extClazz != null) {
            Object object = extClazz.newInstance();
            Method setMethod = extClazz.getDeclaredMethod("say");
            setMethod.invoke(object);
        }

        //测试Context ClassLoader 线程上下文类加载器
        Thread thread = new Thread(() -> {
            //1. 测试子线程的classloader能否加载父线程中加载的class
            try {
                Class subClazz = Thread.currentThread().getContextClassLoader()
                        .loadClass("com.wfw.Speak");
                if (subClazz != null) {
                    Object object = subClazz.newInstance();
                    Method setMethod = subClazz.getDeclaredMethod("say");
                    setMethod.invoke(object);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            //2. 修改子线程的context classloader
            try {
                Thread.currentThread().setContextClassLoader(firstDiskClassLoader);
                Class subReloadedClazz = Thread.currentThread().getContextClassLoader()
                        .loadClass("com.wfw.Speak");
                if (subReloadedClazz != null) {
                    Object object = subReloadedClazz.newInstance();
                    Method setMethod = subReloadedClazz.getDeclaredMethod("say");
                    setMethod.invoke(object);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }
}
