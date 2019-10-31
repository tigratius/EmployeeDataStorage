package com.tigratius.employeedatastorage.service.impl;

import com.tigratius.employeedatastorage.model.Role;
import com.tigratius.employeedatastorage.model.Status;
import com.tigratius.employeedatastorage.model.User;
import com.tigratius.employeedatastorage.repository.RoleRepository;
import com.tigratius.employeedatastorage.repository.UserRepository;
import com.tigratius.employeedatastorage.service.UserService;
import com.tigratius.employeedatastorage.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void save(User user) {

        if (user.getStatus() == null)
        {
            user.setStatus(Status.ACTIVE);
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setCreated(DateUtil.getDateNow());
        user.setUpdated(DateUtil.getDateNow());

        List<Role> userRoles = new ArrayList<>();
        for (Role role : user.getRoles()
        ) {
            Role roleUser = roleRepository.findByName(role.getName());
            userRoles.add(roleUser);
        }

        user.setRoles(userRoles);
        userRepository.save(user);
    }

    @Override
    public void update(Long id, User updatedUser) {
        User user = getById(id);

        if (updatedUser.getUsername() != null) {
            user.setUsername(updatedUser.getUsername());
        }

        if (updatedUser.getPhoneNumber() != null) {
            user.setPhoneNumber(updatedUser.getPhoneNumber());
        }

        if (updatedUser.getStatus() != null) {
            user.setStatus(updatedUser.getStatus());
        }

        if (updatedUser.getPassword() != null) {
            user.setPassword(bCryptPasswordEncoder.encode(updatedUser.getPassword()));
        }

        if (updatedUser.getRoles() != null) {
            List<Role> userRoles = new ArrayList<>();
            for (Role role : updatedUser.getRoles()
            ) {
                Role roleUser = roleRepository.findByName(role.getName());
                userRoles.add(roleUser);
            }

            user.setRoles(userRoles);
        }

        user.setUpdated(DateUtil.getDateNow());

        userRepository.save(user);
    }

    @Override
    public void delete(Long id) {

        User user = getById(id);
        user.setStatus(Status.DELETED);
        user.setUpdated(DateUtil.getDateNow());

        userRepository.save(user);
    }

    @Override
    public User getById(Long id) {
        return userRepository.getOne(id);
    }

    @Override
    public List<User> list() {
        return userRepository.findAll();
    }

    @Override
    public User register(User user) {

        Role roleUser = roleRepository.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);
        user.setStatus(Status.NOT_ACTIVE);

        user.setCreated(DateUtil.getDateNow());
        user.setUpdated(DateUtil.getDateNow());

        User registeredUser = userRepository.save(user);

        log.info("IN register - user: {} successfully registered", registeredUser);

        return registeredUser;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }

    @Override
    public void activate(User user) {
        user.setStatus(Status.ACTIVE);
        user.setUpdated(DateUtil.getDateNow());
        userRepository.save(user);
    }
}
