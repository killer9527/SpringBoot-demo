package com.wfw.study.ioc.annotation;

/**
 * Created by killer9527 on 2018/3/28.
 * 自定义组件A
 */
@MyComponent
public class AComponent {
    @MyResource(name = "bComponentImpl")
    private BComponentInterface bComponent;

    public void print(){
        System.out.println("this is an AComponent, it's dependency bComponent say: "
                + bComponent.say());
    }
}
