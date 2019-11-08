package com.tigratius.employeedatastorage.service.impl;

import com.tigratius.employeedatastorage.builder.CommonBuilder;
import com.tigratius.employeedatastorage.builder.UserBuilder;
import com.tigratius.employeedatastorage.model.Role;
import com.tigratius.employeedatastorage.model.Status;
import com.tigratius.employeedatastorage.model.User;
import com.tigratius.employeedatastorage.repository.RoleRepository;
import com.tigratius.employeedatastorage.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;
import java.util.List;

import static com.tigratius.employeedatastorage.builder.CommonBuilder.id;
import static com.tigratius.employeedatastorage.builder.RoleBuilder.roleDb;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private UserServiceImpl userService;


    @Test
    public void save() {
        Role userRole = roleDb("ROLE_USER").build();

        User user = UserBuilder.userRoles("user1").build();

        when(roleRepository.findByName("ROLE_USER")).thenReturn(userRole);

        when(bCryptPasswordEncoder.encode(anyString())).thenReturn(user.getPassword());

        userService.save(user);

        Assert.assertEquals(user.getStatus(), Status.ACTIVE);
        Assert.assertNotNull(user.getCreated());
        Assert.assertNotNull(user.getUpdated());

        verify(userRepository, times(1)).save(user);

        for (Role role:
                user.getRoles()) {
            verify(roleRepository, times(1)).findByName(role.getName());
        }
    }

    @Test
    public void update() {
        User user = UserBuilder.userDb("user1").build();
        Date timeUpdatedBefore = user.getUpdated();

        Role userRole = roleDb("ROLE_USER").build();

        User updatedUser = UserBuilder.user("updatedUser")
                .phoneNumber("+781000000")
                .password("2")
                .status(Status.NOT_ACTIVE)
                .roles(CommonBuilder.list(userRole))
                .build();

        when(roleRepository.findByName("ROLE_USER")).thenReturn(userRole);
        when(userRepository.getOne(id("1"))).thenReturn(user);
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn(updatedUser.getPassword());

        userService.update(user.getId(), updatedUser);

        Assert.assertEquals(updatedUser.getUsername(), user.getUsername());
        Assert.assertEquals(updatedUser.getPassword(), user.getPassword());
        Assert.assertEquals(updatedUser.getPhoneNumber(), user.getPhoneNumber());
        Assert.assertEquals(updatedUser.getRoles().size(), user.getRoles().size());
        Assert.assertEquals(Status.NOT_ACTIVE, user.getStatus());
        Assert.assertNotEquals(timeUpdatedBefore, user.getUpdated());

        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void delete() {
        User user = UserBuilder.userDb("user1").build();
        Date timeUpdatedBefore = user.getUpdated();
        Date timeCreatedBefore = user.getCreated();

        when(userRepository.getOne(id("1"))).thenReturn(user);

        userService.delete(user.getId());

        Assert.assertEquals(user.getStatus(), Status.DELETED);
        Assert.assertNotEquals(timeUpdatedBefore, user.getUpdated());
        Assert.assertEquals(timeCreatedBefore, user.getCreated());

        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void getById() {
        User user = UserBuilder.userDb("user1").build();

        when(userRepository.getOne(id("1"))).thenReturn(user);

        userService.getById(user.getId());

        Long expectedId = id("1");
        Assert.assertEquals(expectedId, user.getId());

        verify(userRepository, times(1)).getOne(expectedId);
    }

    @Test
    public void getAll() {
        User user1 = UserBuilder.userDb("user1").build();
        User user2 = UserBuilder.userDb("user2").id(CommonBuilder.id("2")).build();
        List<User> users = CommonBuilder.list(user1, user2);

        when(userRepository.findAll()).thenReturn(users);

        List<User> actualResult = userService.list();

        Assert.assertEquals(users.size(), actualResult.size());

        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void register() {

        Role userRole = roleDb("ROLE_USER").build();
        User user = UserBuilder.user("user1").build();

        when(roleRepository.findByName("ROLE_USER")).thenReturn(userRole);
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn(user.getPassword());
        when(userRepository.save(user)).thenReturn(user);

        userService.register(user);

        Assert.assertEquals(user.getStatus(), Status.NOT_ACTIVE);
        Assert.assertNotNull(user.getCreated());
        Assert.assertNotNull(user.getUpdated());
        Assert.assertTrue(user.getRoles().contains(userRole));

        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void findByUsername() {
        String userName = "user1";
        User user = UserBuilder.userDb(userName).build();

        when(userRepository.findByUsername(userName)).thenReturn(user);

        User actualUser = userService.findByUsername(userName);

        Long expectedId = id("1");
        Assert.assertEquals(expectedId, actualUser.getId());

        verify(userRepository, times(1)).findByUsername(anyString());
    }

    @Test
    public void findByPhoneNumber() {
        String phoneNumber = "+711111";
        User user = UserBuilder.userDb("user1").phoneNumber(phoneNumber).build();

        when(userRepository.findByPhoneNumber(phoneNumber)).thenReturn(user);

        User actualUser = userService.findByPhoneNumber(phoneNumber);

        Long expectedId = id("1");
        Assert.assertEquals(expectedId, actualUser.getId());
        Assert.assertEquals(phoneNumber, actualUser.getPhoneNumber());

        verify(userRepository, times(1)).findByPhoneNumber(anyString());
    }

    @Test
    public void activate() {

        User user = UserBuilder.userDb("user1").build();
        Date timeUpdatedBefore = user.getUpdated();
        Date timeCreatedBefore = user.getCreated();

        userService.activate(user);

        Assert.assertEquals(user.getStatus(), Status.ACTIVE);
        Assert.assertNotEquals(timeUpdatedBefore, user.getUpdated());
        Assert.assertEquals(timeCreatedBefore, user.getCreated());

        verify(userRepository, times(1)).save(user);
    }
}