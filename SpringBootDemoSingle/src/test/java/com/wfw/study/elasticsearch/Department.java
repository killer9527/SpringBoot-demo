package com.wfw.study.elasticsearch;

import java.util.List;

/**
 * Created by killer9527 on 2018/4/8.
 */
public class Department {
    private Long id;
    private String departmentName;
    @ElasticProperty(isNested = NestOpinion.NESTED)
    private List<Employee> employees;
    private List<Business> businessList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<Business> getBusinessList() {
        return businessList;
    }

    public void setBusinessList(List<Business> businessList) {
        this.businessList = businessList;
    }
}
