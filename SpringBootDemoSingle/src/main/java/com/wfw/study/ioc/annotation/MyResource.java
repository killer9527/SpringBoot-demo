package com.wfw.study.ioc.annotation;

import java.lang.annotation.*;

/** * Created by killer9527 on 2018/3/27.
 * Field注入注解
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyResource {
    /**
     * 默认是field名称
     * @return
     */
    String name() default "";
}
