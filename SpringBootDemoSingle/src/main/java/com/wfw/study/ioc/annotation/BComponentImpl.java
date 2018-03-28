package com.wfw.study.ioc.annotation;

/**
 * Created by killer9527 on 2018/3/28.
 * 自定义B组件的接口实现
 */
@MyComponent
public class BComponentImpl implements BComponentInterface {
    @Override
    public String say() {
        return "this is a hello from BComponentInterface";
    }
}
