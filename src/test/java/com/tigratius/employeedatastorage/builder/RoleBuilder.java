package com.tigratius.employeedatastorage.builder;

import com.tigratius.employeedatastorage.model.Role;
import com.tigratius.employeedatastorage.model.Status;

import java.util.Date;

public class RoleBuilder {

    private Role role = new Role();

    public static RoleBuilder roleDb(String name) {
        return  role(name)
                .id(CommonBuilder.id("1"))
                .status(Status.ACTIVE)
                .createdDate(CommonBuilder.datetime("2019-11-01 00:00:00"))
                .updatedDate(CommonBuilder.datetime("2019-11-01 00:00:00"));
    }

    public static RoleBuilder role(String name) {
        return new RoleBuilder()
                .name(name);
    }

    public RoleBuilder id(Long id)
    {
        role.setId(id);
        return this;
    }

    public RoleBuilder name(String name) {
        role.setName(name);
        return this;
    }

    public RoleBuilder status(Status status) {
        role.setStatus(status);
        return this;
    }

    public RoleBuilder createdDate(Date date) {
        role.setCreated(date);
        return this;
    }

    public RoleBuilder updatedDate(Date date) {
        role.setUpdated(date);
        return this;
    }

    public Role build() {
        return role;
    }
}
