package com.tigratius.employeedatastorage.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tigratius.employeedatastorage.model.Status;
import com.tigratius.employeedatastorage.model.User;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdminUserDto {

    private Long id;
    private String username;
    private String phoneNumber;
    private String status;
    private String password;

    public User toUser() {
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setPhoneNumber(phoneNumber);
        user.setStatus(Status.valueOf(status));
        user.setPassword(password);
        return user;
    }

    public static AdminUserDto fromUser(User user) {
        AdminUserDto adminUserDto = new AdminUserDto();
        adminUserDto.setId(user.getId());
        adminUserDto.setUsername(user.getUsername());
        adminUserDto.setPhoneNumber(user.getPhoneNumber());
        adminUserDto.setStatus(user.getStatus().name());
        adminUserDto.setPassword(user.getPassword());
        return adminUserDto;
    }
}
