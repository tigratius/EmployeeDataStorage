package com.tigratius.employeedatastorage.builder;

import com.tigratius.employeedatastorage.model.Department;
import com.tigratius.employeedatastorage.model.Status;

import java.util.Date;

public class DepartmentBuilder {

    private Department department = new Department();

    public static DepartmentBuilder departmentDb(String name) {
        return  department(name)
                .id(CommonBuilder.id("1"))
                .status(Status.ACTIVE)
                .createdDate(CommonBuilder.datetime("2019-11-01 00:00:00"))
                .updatedDate(CommonBuilder.datetime("2019-11-01 00:00:00"));
    }

    public static DepartmentBuilder department(String name) {
        return new DepartmentBuilder()
                .name(name);
    }

    public DepartmentBuilder id(Long id)
    {
        department.setId(id);
        return this;
    }

    public DepartmentBuilder name(String name) {
        department.setName(name);
        return this;
    }

    public DepartmentBuilder status(Status status) {
        department.setStatus(status);
        return this;
    }

    public DepartmentBuilder createdDate(Date date) {
        department.setCreated(date);
        return this;
    }

    public DepartmentBuilder updatedDate(Date date) {
        department.setUpdated(date);
        return this;
    }

    public Department build() {
        return department;
    }
}
