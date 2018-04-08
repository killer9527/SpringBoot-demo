package com.wfw.study.elasticsearch;

import java.util.Date;
import java.util.List;

/**
 * Created by killer9527 on 2018/4/8.
 */
public class Employee {
    private String name;
    private Integer age;
    @ElasticProperty(fieldIndex = FieldIndexOption.ANALYZED, fields = "{\"raw\": {\"type\": \"keyword\"}}")
    private String profile;
    @ElasticProperty(dateFormat = "yyyy-MM-dd")
    private Date birth;
    private List<String> articles;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public List<String> getArticles() {
        return articles;
    }

    public void setArticles(List<String> articles) {
        this.articles = articles;
    }
}
