package com.tigratius.employeedatastorage.service.impl;

import com.tigratius.employeedatastorage.model.Role;
import com.tigratius.employeedatastorage.repository.RoleRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static com.tigratius.employeedatastorage.builder.CommonBuilder.*;
import static com.tigratius.employeedatastorage.builder.RoleBuilder.roleDb;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RoleServiceImplTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    @Test
    public void getAll() {
        Role userRole = roleDb("USER_ROLE").build();
        Role adminRole = roleDb("USER_ADMIN").id(id("2")).build();
        List<Role> roles = list(userRole, adminRole);

        when(roleRepository.findAll()).thenReturn(roles);

        List<Role> actualResult = roleService.list();

        Assert.assertEquals(roles.size(), actualResult.size());

        verify(roleRepository, times(1)).findAll();
    }
}