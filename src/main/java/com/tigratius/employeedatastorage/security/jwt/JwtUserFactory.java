package com.tigratius.employeedatastorage.security.jwt;

import com.tigratius.employeedatastorage.model.Role;
import com.tigratius.employeedatastorage.model.Status;
import com.tigratius.employeedatastorage.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class  JwtUserFactory {

    public JwtUserFactory() {
    }

    public static JwtUser create(User user)
    {
        return new JwtUser(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getPhoneNumber(),
                user.getStatus().equals(Status.ACTIVE),
                user.getUpdated(),
                mapToGrantedAuthorities(user.getRoles())
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> userRoles) {
        return userRoles.stream()
                .map(role ->
                        new SimpleGrantedAuthority(role.getName())
                ).collect(Collectors.toList());
    }
}
