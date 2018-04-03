package com.wfw.study;

import com.wfw.study.ioc.annotation.AComponent;
import com.wfw.study.ioc.annotation.BComponentInterface;
import com.wfw.study.ioc.annotation.MyAnnotationBeanFactory;
import com.wfw.study.ioc.xml.AInterface;
import com.wfw.study.ioc.xml.BInterface;
import com.wfw.study.ioc.xml.MyXmlBeanFactory;
import junit.framework.TestCase;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by killer9527 on 2018/3/26.
 */
public class MyBeanFactoryTest extends TestCase {
    public void testApplicationContext() {
        //测试ClassPathXmlApplicationContext
        ApplicationContext ac = new ClassPathXmlApplicationContext("study/beans.xml");
        BInterface bInterface = (BInterface) ac.getBean("bInterface");
        System.out.println(bInterface.say());
        ((AInterface) ac.getBean("aInterface")).print();
    }

    public void testXmlBeanFactory() throws Exception {
        MyXmlBeanFactory myXmlBeanFactory = new MyXmlBeanFactory("study/beans.xml");
        BInterface bInterface = (BInterface) myXmlBeanFactory.getBean("bInterface");
        System.out.println(bInterface.say());
        ((AInterface) myXmlBeanFactory.getBean("aInterface")).print();
    }

    public void testAnnotationBeanFactory() throws Exception {
        MyAnnotationBeanFactory myAnnotationBeanFactory = new MyAnnotationBeanFactory("com.wfw.study.ioc.annotation");
        BComponentInterface bComponentInterface = (BComponentInterface) myAnnotationBeanFactory.getBean("bComponentImpl");
        System.out.println(bComponentInterface.say());
        AComponent aComponent = (AComponent) myAnnotationBeanFactory.getBean("aComponent");
        aComponent.print();
    }
}
