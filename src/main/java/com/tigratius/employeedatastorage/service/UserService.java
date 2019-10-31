package com.tigratius.employeedatastorage.service;

import com.tigratius.employeedatastorage.model.User;

public interface UserService extends BaseService<User>{

    User register(User user);

    User findByUsername(String username);

    User findByPhoneNumber(String phoneNumber);

    void activate(User user);
}
