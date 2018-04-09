package com.wfw.utils;

import com.wfw.study.elasticsearch.Department;
import com.wfw.study.elasticsearch.ElasticsearchMappingGenerator;
import org.elasticsearch.client.Client;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by killer9527 on 2018/4/4.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ESClientHelperTest {
    @Autowired
    private Environment env;

    @Test
    public void testCreateIndex(){
        Client client = ESClientHelper.getClient(env.getProperty("elasticsearch.cluster-name2"));
        String index = env.getProperty("elasticsearch.index-name");
        String mapping = ElasticsearchMappingGenerator.generateSource(Department.class, null, false);
        if (ESClientHelper.createIndex(client, index, null, "department", mapping)){
            System.out.println("success creating index");
        }else{
            System.out.println("fail to create index");
        }
    }
}
