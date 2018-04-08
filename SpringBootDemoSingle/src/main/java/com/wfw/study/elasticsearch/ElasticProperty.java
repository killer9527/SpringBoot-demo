package com.wfw.study.elasticsearch;

import java.lang.annotation.*;

/**
 * Created by killer9527 on 2018/4/8.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ElasticProperty {
    /**
     * 是否分词，默认不分词
     * @return
     */
    FieldIndexOption fieldIndex() default FieldIndexOption.NOTANALYZED;

    /**
     * 该字段分词时使用的分词器，默认使用ik_max_word
     * @return
     */
    String analyzer() default "ik_max_word";

    /**
     * 当字段为null时的默认值，默认为“-”
     * @return
     */
    String nullValue() default "-";

    /**
     * 日期格式
     * @return
     */
    String dateFormat() default "yyyy-MM-dd HH:mm:ss";

    /**
     * fields
     * @return
     */
    String fields() default "";

    /**
     * 是否为nested字段
     * @return
     */
    NestOpinion isNested() default NestOpinion.NOTNESTED;
}
