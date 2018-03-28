package com.wfw.study.ioc.annotation;

import java.lang.annotation.*;

/**
 * Created by killer9527 on 2018/3/27.
 * 自定义组件注解
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyComponent {
    /**
     * 组件名称
     * @return
     */
    String value() default "";
}
