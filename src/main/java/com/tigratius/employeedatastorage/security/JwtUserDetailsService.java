package com.tigratius.employeedatastorage.security;

import com.tigratius.employeedatastorage.exceptions.PhoneVerificationException;
import com.tigratius.employeedatastorage.model.Status;
import com.tigratius.employeedatastorage.model.User;
import com.tigratius.employeedatastorage.security.jwt.JwtUser;
import com.tigratius.employeedatastorage.security.jwt.JwtUserFactory;
import com.tigratius.employeedatastorage.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public JwtUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, PhoneVerificationException {
        User user = userService.findByUsername(username);

        if (user == null || user.getStatus() == Status.DELETED) {
            throw new UsernameNotFoundException("User with username: " + username + " not found");
        }

        if (user.getStatus() == Status.NOT_ACTIVE) {
            throw new PhoneVerificationException("User with username: " + username + " not verified");
        }

        JwtUser jwtUser = JwtUserFactory.create(user);
        log.info("IN loadUserByUsername - user with username: {} successfully loaded", username);
        return jwtUser;
    }
}
