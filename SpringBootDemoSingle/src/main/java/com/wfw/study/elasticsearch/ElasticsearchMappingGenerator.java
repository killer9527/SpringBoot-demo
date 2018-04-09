package com.wfw.study.elasticsearch;

import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

/**
 * Created by killer9527 on 2018/4/8.
 * 创建es索引时的mapping生成器
 */
public class ElasticsearchMappingGenerator {
    private final static String mappingPattern = "\"$type\": {\"properties\": {$properties}}";
    private final static String nestedMappingPattern = "\"$type\": {\"type\": \"nested\", \"properties\": {$properties}}";
    private final static String propertyPattern = "\"$fieldName\": {$parameters}";
    private final static String parameterPattern = "\"$parameterName\": \"$parameterValue\"";
    private final static String fieldsPattern = "\"fields\": $fieldsValue";


    /**
     * 生成type对应的source
     * @param clazz：定义的类模型
     * @param fieldName：为null时，则只有properties，即为source；不为null时，表示要生成模型中某个字段的source
     * @param isNested：fieldName不为null时有意义，true表示该字段为nested
     * @return
     */
    public static String generateSource(Class<?> clazz, String fieldName, boolean isNested){
        String result;
        StringBuilder properties = new StringBuilder();

        if (fieldName == null) {
            //主resource只有properties
            result = mappingPattern.replace("\"$type\": ", "");
        }else{
            //字段对应的resource
            if (isNested){
                //nested字段
                result = nestedMappingPattern.replace("$type", fieldName);
            }else{
                result = mappingPattern.replace("$type", fieldName);
            }
        }

        Field[] fields = clazz.getDeclaredFields();
        boolean isFirst = true;
        for (Field field : fields) {
            if (isFirst) {
                properties.append(generateProperty(field));
                isFirst = false;
            } else {
                properties.append(", ").append(generateProperty(field));
            }
        }
        return result.replace("$properties", properties.toString());
    }

    /**
     * 获取字段的mapping
     *
     * @param field
     * @return
     */
    private static String generateProperty(Field field) {
        String fieldName = field.getName();
        //获取字段类型
        Class<?> fieldType = field.getType();

        //获取@ElasticProperty注解
        ElasticProperty elasticProperty = field.getAnnotation(ElasticProperty.class);
        String type;

        //field为列表类型
        if (fieldType.equals(List.class)) {
            //Array dataType
            Type genericType = field.getGenericType();
            if (genericType instanceof ParameterizedType){
                fieldType = (Class)((ParameterizedType)genericType).getActualTypeArguments()[0];
            }
        }
        //字段为String类型，需要判断是否分词
        if (fieldType.equals(String.class)) {
            if (elasticProperty != null && elasticProperty.fieldIndex().equals(FieldIndexOption.ANALYZED)) {
                type = "text";
            } else {
                type = "keyword";
            }
        } else if (fieldType.equals(Integer.class)) {
            type = "integer";
        } else if (fieldType.equals(Long.class)) {
            type = "long";
        } else if (fieldType.equals(Double.class)) {
            type = "double";
        } else if (fieldType.equals(Float.class)) {
            type = "float";
        } else if (fieldType.equals(Boolean.class)) {
            type = "boolean";
        } else if (fieldType.equals(Byte.class)) {
            type = "binary";
        } else if (fieldType.equals(Date.class)) {
            type = "date";
        } else {
            //如果是Nested dataType类型则需要重新赋值
            if (elasticProperty!=null && elasticProperty.isNested().equals(NestOpinion.NESTED)) {
                //注意此时isNested参数为true
                return generateSource(fieldType, fieldName, true);
            }
            //Object dataType：自定义类型字段，使用递归获取该field的source
            return generateSource(fieldType, fieldName, false);
        }

        //单个字段上定义的参数信息
        StringBuilder parameters = new StringBuilder();
        parameters.append(parameterPattern.replace("$parameterName", "type").replace("$parameterValue", type));
        if (elasticProperty != null) {
            if (type.equals("fulltext")) {
                String analyzer = elasticProperty.analyzer();
                parameters.append(", ")
                        .append(parameterPattern.replace("$parameterName", "analyzer").replace("$parameterValue", analyzer));
            }
            if (type.equals("keyword") || type.equals("date")) {
                String nullValue = elasticProperty.nullValue();
                parameters.append(", ")
                        .append(parameterPattern.replace("$parameterName", "null_value").replace("$parameterValue", nullValue));
            }
            if (type.equals("date")) {
                String dateFormat = elasticProperty.dateFormat();
                parameters.append(", ")
                        .append(parameterPattern.replace("$parameterName", "format").replace("$parameterValue", dateFormat));
            }
            if (!StringUtils.isEmpty(elasticProperty.fields())) {
                parameters.append(", ")
                        .append(fieldsPattern.replace("$fieldsValue", elasticProperty.fields()));
            }
        }

        String result = propertyPattern.replace("$fieldName", fieldName).replace("$parameters", parameters.toString());
        return result;
    }
}
