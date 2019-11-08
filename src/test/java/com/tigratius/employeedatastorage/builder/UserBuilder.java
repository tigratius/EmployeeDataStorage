package com.tigratius.employeedatastorage.builder;

import com.tigratius.employeedatastorage.model.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class UserBuilder {

    private User user = new User();

    public static UserBuilder user(String username) {
        return new UserBuilder()
                .username(username)
                .phoneNumber("+791111111")
                .password("1");
    }

    public static UserBuilder userDb(String username) {
        return user(username)
                .id(CommonBuilder.id("1"))
                .roles(CommonBuilder.list(
                        RoleBuilder.roleDb("ROLE_USER").build()))
                .status(Status.NOT_ACTIVE)
                .createdDate(new Date())
                .updatedDate(new Date());
    }

    public static UserBuilder userRoles(String username) {
        return user(username)
                .roles(CommonBuilder.list(
                        RoleBuilder.role("ROLE_USER").build()));
    }

    public UserBuilder id(Long id)
    {
        user.setId(id);
        return this;
    }

    public UserBuilder username(String username) {
        user.setUsername(username);
        return this;
    }

    public UserBuilder phoneNumber(String phoneNumber) {
        user.setPhoneNumber(phoneNumber);
        return this;
    }

    public UserBuilder password(String password) {
        user.setPassword(password);
        return this;
    }


    public UserBuilder status(Status status) {
        user.setStatus(status);
        return this;
    }

    public UserBuilder createdDate(Date date) {
        user.setCreated(date);
        return this;
    }

    public UserBuilder updatedDate(Date date) {
        user.setUpdated(date);
        return this;
    }

    public UserBuilder roles(List<Role> roles)
    {
        user.setRoles(roles);
        return this;
    }

    public User build() {
        return user;
    }
}
