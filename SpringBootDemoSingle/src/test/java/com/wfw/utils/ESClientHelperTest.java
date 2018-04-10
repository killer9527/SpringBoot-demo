package com.wfw.utils;

import com.wfw.study.elasticsearch.Business;
import com.wfw.study.elasticsearch.Department;
import com.wfw.study.elasticsearch.ElasticsearchMappingGenerator;
import com.wfw.study.elasticsearch.Employee;
import org.elasticsearch.client.Client;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Test
    public void testInsert() throws Exception{
        Client client = ESClientHelper.getClient(env.getProperty("elasticsearch.cluster-name2"));
        String index = env.getProperty("elasticsearch.index-name");

        Department department = new Department();
        List<Employee> employees = new ArrayList<>();
        Employee employee = new Employee();
        employee.setName("wfw");
        employee.setAge(29);
        employee.setBirth(new SimpleDateFormat("yyyy-MM-dd").parse("1989-08-01"));
        employee.setProfile("职场菜鸟");
        List<String> articles = new ArrayList<>();
        articles.add("SpringBoot系列");
        articles.add("机器学习了解");
        employee.setArticles(articles);
        employees.add(employee);
        department.setId(1L);
        department.setDepartmentName("技术部");
        department.setEmployees(employees);
        List<Business> businessList = new ArrayList<>();
        Business business = new Business();
        business.setCore("司法111");
        business.setRevenue(new Float(10000000.0));
        businessList.add(business);
        department.setBusinessList(businessList);

        System.out.println(ESClientHelper.insert(client, index, "department", department));
    }
}
