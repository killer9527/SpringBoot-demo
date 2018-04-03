package com.wfw.study.construct;

/**
 * Created by killer9527 on 2018/4/1.
 */
public class ClassB extends ClassA {
    private final static String childStaticVariable = "childStaticVariableValue";
    private String childVariable = "childVariableValue";
    static {
        System.out.println("childStaticVariable: " + childStaticVariable);
        System.out.println("ClassB static code block");
    }

    {
        System.out.println("childVariable: " + childVariable);
        System.out.println("ClassB block code");
    }

    public ClassB(){
        System.out.println("ClassB's constructor with no parameters");
    }

    public ClassB(String para){
        super(para);
        System.out.println("ClassB's constructor with parameters: " + para);
    }
}
