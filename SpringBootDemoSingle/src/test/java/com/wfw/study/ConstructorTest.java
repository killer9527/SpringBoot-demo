package com.wfw.study;

import com.wfw.study.construct.ClassB;
import junit.framework.TestCase;

/**
 * Created by killer9527 on 2018/4/1.
 */
public class ConstructorTest extends TestCase{
    public void testConstructor(){
        ClassB classB = new ClassB();
        System.out.println("---------------------------------------------------");
        classB = new ClassB("ClassB构造参数");
    }
}
