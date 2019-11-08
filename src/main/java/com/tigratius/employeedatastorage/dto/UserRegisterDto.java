package com.tigratius.employeedatastorage.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tigratius.employeedatastorage.model.User;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRegisterDto {
    private Long id;
    private String username;
    private String phoneNumber;
    private String password;

    public User toUser(){
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setPhoneNumber(phoneNumber);
        user.setPassword(password);
        return user;
    }

    /*public static UserRegisterDto fromUser(User user) {
        UserRegisterDto userDto = new UserRegisterDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setPassword(user.getPassword());
        return userDto;
    }*/
}
