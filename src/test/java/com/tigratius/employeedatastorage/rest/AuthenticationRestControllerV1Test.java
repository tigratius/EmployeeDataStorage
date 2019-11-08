package com.tigratius.employeedatastorage.rest;

import com.tigratius.employeedatastorage.builder.CommonBuilder;
import com.tigratius.employeedatastorage.builder.UserBuilder;
import com.tigratius.employeedatastorage.dto.AuthenticationRequestDto;
import com.tigratius.employeedatastorage.dto.UserRegisterDto;
import com.tigratius.employeedatastorage.dto.UserVerifyDto;
import com.tigratius.employeedatastorage.exceptions.PhoneVerificationException;
import com.tigratius.employeedatastorage.model.Department;
import com.tigratius.employeedatastorage.model.User;
import com.tigratius.employeedatastorage.security.jwt.JwtTokenProvider;
import com.tigratius.employeedatastorage.service.PhoneVerificationService;
import com.tigratius.employeedatastorage.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;

import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationRestControllerV1Test {

    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtTokenProvider jwtTokenProvider;
    @Mock
    private UserService userService;
    @Mock
    private PhoneVerificationService phoneVerificationService;

    @InjectMocks
    private AuthenticationRestControllerV1 authenticationRestController;

    @Test
    public void login() {

        User user  = UserBuilder.userDb("userName").build();

        AuthenticationRequestDto userDto = new AuthenticationRequestDto();
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());

        //when(authenticationManager.authenticate(any())).thenReturn();
        String tokenNumber = "tokenNumber";
        when(userService.findByUsername(userDto.getUsername())).thenReturn(user);
        when(jwtTokenProvider.createToken(user.getUsername(), user.getRoles())).thenReturn(tokenNumber);

        ResponseEntity<Map<Object, Object>> response = authenticationRestController.login(userDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user.getUsername(), response.getBody().get("username"));
        assertEquals(tokenNumber, response.getBody().get("token"));

        verify(authenticationManager, times(1)).authenticate(any());
    }

    @Test
    public void loginFailed() {

        User user  = UserBuilder.userDb("userName").build();

        AuthenticationRequestDto userDto = new AuthenticationRequestDto();
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());

        when(userService.findByUsername(userDto.getUsername())).thenReturn(null);

        ResponseEntity<Map<Object, Object>> response = authenticationRestController.login(userDto);

        String message = (String) response.getBody().get("message");
        assertTrue(message.contains("User with username"));
    }

    @Test
    public void registerUserAccount() {

        User user  = UserBuilder.userDb("userName").build();

        UserRegisterDto userRegisterDto = new UserRegisterDto();
        userRegisterDto.setId(user.getId());
        userRegisterDto.setPassword(user.getPassword());
        userRegisterDto.setUsername(user.getUsername());
        userRegisterDto.setPhoneNumber(user.getPhoneNumber());

        when(userService.findByUsername(userRegisterDto.getUsername())).thenReturn(null);
        when(userService.findByPhoneNumber(userRegisterDto.getPhoneNumber())).thenReturn(null);

        ResponseEntity<Map<Object, Object>> response = authenticationRestController.registerUserAccount(userRegisterDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User registered successfully", response.getBody().get("message"));
    }

    @Test(expected = BadCredentialsException.class)
    public void failedRegisterUserAccountByExistingUsername() {

        User user  = UserBuilder.userDb("userName").build();

        UserRegisterDto userRegisterDto = new UserRegisterDto();
        userRegisterDto.setId(user.getId());
        userRegisterDto.setPassword(user.getPassword());
        userRegisterDto.setUsername(user.getUsername());
        userRegisterDto.setPhoneNumber(user.getPhoneNumber());

        when(userService.findByUsername(userRegisterDto.getUsername())).thenReturn(user);

        authenticationRestController.registerUserAccount(userRegisterDto);
    }

    @Test(expected = BadCredentialsException.class)
    public void failedRegisterUserAccountByExistingPhoneNumber() {
        User user  = UserBuilder.userDb("userName").build();

        UserRegisterDto userRegisterDto = new UserRegisterDto();
        userRegisterDto.setId(user.getId());
        userRegisterDto.setPassword(user.getPassword());
        userRegisterDto.setUsername(user.getUsername());
        userRegisterDto.setPhoneNumber(user.getPhoneNumber());

        when(userService.findByUsername(userRegisterDto.getUsername())).thenReturn(null);
        when(userService.findByPhoneNumber(userRegisterDto.getPhoneNumber())).thenReturn(user);

        authenticationRestController.registerUserAccount(userRegisterDto);
    }

    @Test
    public void verifyPhoneNumber() {
        User user  = UserBuilder.userDb("userName").build();

        UserVerifyDto userVerifyDto = new UserVerifyDto();
        userVerifyDto.setCode("1234");
        userVerifyDto.setPhoneNumber(user.getPhoneNumber());

        when(userService.findByPhoneNumber(userVerifyDto.getPhoneNumber())).thenReturn(user);

        ResponseEntity<Map<Object, Object>> response = authenticationRestController.verifyPhoneNumber(userVerifyDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        String message = (String) response.getBody().get("message");
        assertTrue(message.contains("The code has been sent on phone number"));

        verify(phoneVerificationService, times(1)).sendCodeSms(userVerifyDto.getPhoneNumber());
    }

    @Test(expected = PhoneVerificationException.class)
    public void verifyPhoneNumberFailed() {
        User user  = UserBuilder.userDb("userName").build();

        UserVerifyDto userVerifyDto = new UserVerifyDto();
        userVerifyDto.setCode("1234");
        userVerifyDto.setPhoneNumber(user.getPhoneNumber());

        when(userService.findByPhoneNumber(userVerifyDto.getPhoneNumber())).thenReturn(null);

        authenticationRestController.verifyPhoneNumber(userVerifyDto);
    }

    @Test
    public void checkCode() {

        User user  = UserBuilder.userDb("userName").build();

        UserVerifyDto userVerifyDto = new UserVerifyDto();
        userVerifyDto.setCode("1234");
        userVerifyDto.setPhoneNumber(user.getPhoneNumber());

        when(userService.findByPhoneNumber(userVerifyDto.getPhoneNumber())).thenReturn(user);
        when(phoneVerificationService.verifyCode(userVerifyDto.getPhoneNumber(), userVerifyDto.getCode())).thenReturn(true);

        ResponseEntity<Map<Object, Object>> response = authenticationRestController.checkCode(userVerifyDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userService, times(1)).activate(user);
    }

    @Test(expected = PhoneVerificationException.class)
    public void checkCodeUserNotFound() {

        User user  = UserBuilder.userDb("userName").build();

        UserVerifyDto userVerifyDto = new UserVerifyDto();
        userVerifyDto.setCode("1234");
        userVerifyDto.setPhoneNumber(user.getPhoneNumber());

        when(userService.findByPhoneNumber(userVerifyDto.getPhoneNumber())).thenReturn(null);

        authenticationRestController.checkCode(userVerifyDto);
        verify(userService, never()).activate(user);
    }

    @Test
    public void checkCodeInvalid() {

        User user  = UserBuilder.userDb("userName").build();

        UserVerifyDto userVerifyDto = new UserVerifyDto();
        userVerifyDto.setCode("1234");
        userVerifyDto.setPhoneNumber(user.getPhoneNumber());

        when(userService.findByPhoneNumber(userVerifyDto.getPhoneNumber())).thenReturn(user);
        when(phoneVerificationService.verifyCode(userVerifyDto.getPhoneNumber(), userVerifyDto.getCode())).thenReturn(false);

        ResponseEntity<Map<Object, Object>> response = authenticationRestController.checkCode(userVerifyDto);
        assertEquals("The code is invalid", response.getBody().get("message"));
        verify(userService, never()).activate(user);
    }
}