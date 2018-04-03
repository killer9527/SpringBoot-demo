package com.wfw.study.construct;

/**
 * Created by killer9527 on 2018/4/1.
 */
public class ClassA {
    private final static String parentStaticVariable = "parentStaticVariableValue";
    private String parentVariable = "parentVariableValue";
    static {
        System.out.println("parentStaticVariableValue: " + parentStaticVariable);
        System.out.println("ClassA static code block");
    }

    {
        System.out.println("parentVariable: " + parentVariable);
        System.out.println("ClassA code block");
    }

    public ClassA(){
        System.out.println("ClassA's constructor with no parameters");
    }

    public ClassA(String para){
        System.out.println("ClassA's constructor with parameters: " + para);
    }
}
