package com.tigratius.employeedatastorage.service;

public interface PhoneVerificationService {

    void sendCodeSms(String phoneNumber);

    boolean verifyCode(String phoneNumber, String code);
}
