package com.wfw.study.ioc.xml;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by killer9527 on 2018/3/26.
 */
public class MyXmlBeanFactory {
    private Map<String, Object> container = new HashMap<>();

    public MyXmlBeanFactory(String... xmlPaths) throws DocumentException, ClassNotFoundException,
            IllegalAccessException, InstantiationException, NoSuchFieldException {
        for (String xmlPath : xmlPaths) {
            // 读取指定的配置文件
            SAXReader reader = new SAXReader();
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            // 从class目录下获取指定的xml文件
            InputStream ins = classLoader.getResourceAsStream(xmlPath);
            Document doc = reader.read(ins);
            Element root = doc.getRootElement();
            Element foo;
            // 遍历bean
            for (Iterator i = root.elementIterator("bean"); i.hasNext(); ) {
                foo = (Element) i.next();
                // 获取bean的属性id和class
                Attribute id = foo.attribute("id");
                Attribute cls = foo.attribute("class");
                // 利用Java反射机制，通过class的名称获取Class对象
                Class<?> bean = Class.forName(cls.getText());
                // 创建一个对象
                Object obj = bean.newInstance();
                // 遍历该bean的property属性
                for (Iterator ite = foo.elementIterator("property"); ite.hasNext(); ) {
                    Element foo2 = (Element) ite.next();
                    // 获取该property的name属性
                    Attribute name = foo2.attribute("name");
                    Object value;
                    if (container.containsKey(name.getText())) {
                        value = container.get(name.getText());
                        Field field = bean.getDeclaredField(name.getText());
                        field.setAccessible(true);
                        field.set(obj, value);
                    }
                }
                // 将对象放入beanMap中，其中key为id值，value为对象
                container.put(id.getText(), obj);
            }
        }
    }

    public Object getBean(String beanName) {
        Object obj = this.container.get(beanName);
        return obj;
    }
}
