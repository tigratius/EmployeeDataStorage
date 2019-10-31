package com.tigratius.employeedatastorage.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tigratius.employeedatastorage.model.Role;
import com.tigratius.employeedatastorage.model.User;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {

    private Long id;
    private String username;
    private String phoneNumber;
    private List<RolesDto> roles;

    public User toUser(){
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setPhoneNumber(phoneNumber);
        return user;
    }

    public static UserDto fromUser(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setPhoneNumber(user.getPhoneNumber());

        List<RolesDto> roles = new ArrayList<>();

        for (Role role: user.getRoles()
             ) {
            roles.add(RolesDto.fromRole(role));
        }
        userDto.setRoles(roles);
        return userDto;
    }

    public static List<UserDto> toUserDtos(List<User> users){

        List<UserDto> userDtoList = new ArrayList<>();

        for (User user:users) {
            userDtoList.add(fromUser(user));
        }
        return userDtoList;
    }
}
