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

    public static String generateMapping(Class<?> clazz, String indexType, boolean isNested){
        String result;
        StringBuilder properties = new StringBuilder();

        //类名为index的type
        if (indexType == null) {
            indexType = clazz.getSimpleName().toLowerCase();
        }
        if (isNested){
            result = nestedMappingPattern.replace("$type", indexType);
        }else{
            result = mappingPattern.replace("$type", indexType);
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
        StringBuilder parameters = new StringBuilder();
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

        if (fieldType.equals(String.class)) {
            if (elasticProperty != null && elasticProperty.fieldIndex().equals(FieldIndexOption.ANALYZED)) {
                type = "fulltext";
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
                return generateMapping(fieldType, fieldName, true);
            }
            //Object dataType：使用递归获取该field的mapping
            return generateMapping(fieldType, fieldName, false);
        }

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
