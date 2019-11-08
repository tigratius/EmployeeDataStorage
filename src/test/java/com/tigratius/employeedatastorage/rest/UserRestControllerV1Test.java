package com.tigratius.employeedatastorage.rest;

import com.tigratius.employeedatastorage.builder.CommonBuilder;
import com.tigratius.employeedatastorage.builder.UserBuilder;
import com.tigratius.employeedatastorage.dto.UserDto;
import com.tigratius.employeedatastorage.model.User;
import com.tigratius.employeedatastorage.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class UserRestControllerV1Test {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserRestControllerV1 userRestController;

    @Test
    public void get() {

        User user = UserBuilder.userDb("userName").build();

        when(userService.getById(user.getId())).thenReturn(user);
        ResponseEntity<UserDto> response = userRestController.get(user.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user.getUsername(), Objects.requireNonNull(response.getBody()).getUsername());
        assertEquals(user.getPhoneNumber(), Objects.requireNonNull(response.getBody()).getPhoneNumber());
        assertEquals(user.getId(), response.getBody().getId());
    }

    @Test
    public void getFail() {

        ResponseEntity<UserDto> response1 = userRestController.get(null);
        assertEquals(HttpStatus.BAD_REQUEST, response1.getStatusCode());

        Long id = CommonBuilder.id("1");
        when(userService.getById(id)).thenReturn(null);
        ResponseEntity<UserDto> response2 = userRestController.get(id);

        assertEquals( HttpStatus.NOT_FOUND, response2.getStatusCode());
    }

    @Test
    public void getAll() {
        User user1 = UserBuilder.userDb("username1").build();
        User user2 = UserBuilder.userDb("username2").id(CommonBuilder.id("2")).build();
        List<User> users = CommonBuilder.list(user1, user2);

        when(userService.list()).thenReturn(users);

        ResponseEntity<List<UserDto>> response = userRestController.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(users.size(), Objects.requireNonNull(response.getBody()).size());
        assertEquals(user1.getId(), response.getBody().get(0).getId());
        assertEquals(user2.getId(), response.getBody().get(1).getId());
    }

    @Test
    public void getAllFail() {

        when(userService.list()).thenReturn(new ArrayList<>());
        ResponseEntity<List<UserDto>> response = userRestController.getAll();
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void save() {

        User user = UserBuilder.user("username1").build();

        ResponseEntity<User> response =  userRestController.save(user);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(user.getUsername(), Objects.requireNonNull(response.getBody()).getUsername());
        assertEquals(user.getPhoneNumber(), Objects.requireNonNull(response.getBody()).getPhoneNumber());

        verify(userService, times(1)).save(user);
    }

    @Test
    public void saveFail() {
        ResponseEntity<User> response =  userRestController.save(null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        verify(userService, never()).save(any(User.class));
    }

    @Test
    public void update() {
        User user = UserBuilder.user("username2").build();

        Long id  = CommonBuilder.id("1");
        ResponseEntity<User> response =  userRestController.update(id, user);

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(user.getUsername(), Objects.requireNonNull(response.getBody()).getUsername());
        assertEquals(user.getPhoneNumber(), Objects.requireNonNull(response.getBody()).getPhoneNumber());

        verify(userService, times(1)).update(id, user);
    }

    @Test
    public void updateFail() {
        ResponseEntity<User> response =  userRestController.update(null, null);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        verify(userService, never()).update(anyLong(), any(User.class));
    }

    @Test
    public void delete() {

        User user = UserBuilder.userDb("username1").build();
        when(userService.getById(user.getId())).thenReturn(user);

        ResponseEntity response =  userRestController.delete(user.getId());

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());

        verify(userService, times(1)).delete(user.getId());
    }

    @Test
    public void deleteFail() {

        Long id = CommonBuilder.id("1");
        when(userService.getById(id)).thenReturn(null);

        ResponseEntity response =  userRestController.delete(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(userService, never()).delete(anyLong());
    }
}