package com.wfw.study.elasticsearch;

import java.lang.annotation.*;

/** * Created by killer9527 on 2018/4/10.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ElasticType {
    /**
     * 指定索引id为哪个字段，如果为空，则随机
     * @return
     */
    String id() default "";
}
