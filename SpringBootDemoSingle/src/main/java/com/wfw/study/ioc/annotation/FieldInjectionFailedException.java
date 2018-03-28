package com.wfw.study.ioc.annotation;

/**
 * Created by killer9527 on 2018/3/28.
 */
public class FieldInjectionFailedException extends Exception {
    public FieldInjectionFailedException(String message){
        super(message);
    }
}
