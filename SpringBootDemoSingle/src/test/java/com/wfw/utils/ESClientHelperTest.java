package com.wfw.utils;

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
        System.out.println(ESClientHelper.indexExists(client, "index"));
    }
}
