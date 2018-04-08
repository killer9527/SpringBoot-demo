package com.wfw.study.elasticsearch;

import junit.framework.TestCase;

/**
 * Created by killer9527 on 2018/4/8.
 */
public class MappingGeneratorTest extends TestCase{
    public void testGenerateMapping(){
        System.out.println(ElasticsearchMappingGenerator.generateMapping(Department.class, null, false));
    }
}
