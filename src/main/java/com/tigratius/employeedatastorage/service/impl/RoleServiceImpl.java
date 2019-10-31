package com.tigratius.employeedatastorage.service.impl;

import com.tigratius.employeedatastorage.model.Role;
import com.tigratius.employeedatastorage.repository.RoleRepository;
import com.tigratius.employeedatastorage.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> list() {
        return roleRepository.findAll();
    }
}
