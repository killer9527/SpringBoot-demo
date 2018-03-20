package com.wfw.entity;

public class Department {
    private Integer id;

    private String departmentName;

    private String departmentCulture;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName == null ? null : departmentName.trim();
    }

    public String getDepartmentCulture() {
        return departmentCulture;
    }

    public void setDepartmentCulture(String departmentCulture) {
        this.departmentCulture = departmentCulture == null ? null : departmentCulture.trim();
    }
}