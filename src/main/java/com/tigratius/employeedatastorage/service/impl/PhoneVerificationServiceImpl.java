package com.tigratius.employeedatastorage.service.impl;

import com.tigratius.employeedatastorage.service.PhoneVerificationService;
import com.twilio.Twilio;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@Slf4j
public class PhoneVerificationServiceImpl implements PhoneVerificationService {

    private String sid;

    @Value("${twilio.account.sid}")
    private String ACCOUNT_SID;

    @Value("${twilio.auth.token}")
    private String AUTH_TOKEN;

    public PhoneVerificationServiceImpl() {
    }

    @PostConstruct
    protected void init()
    {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    public void sendCodeSms(String phoneNumber) {

        com.twilio.rest.verify.v2.Service service = com.twilio.rest.verify.v2.Service.creator("Verify Service").create();

        sid = service.getSid();

        log.info("Service Info:");
        log.info(service + "");

        Verification verification = Verification.creator(
                sid,
                phoneNumber,
                "sms")
                .create();

        log.info("Verification Info:");
        log.info(verification + "");
    }

    public boolean verifyCode(String phoneNumber, String code){

        VerificationCheck verificationCheck = VerificationCheck.creator(
                sid,
                code)
                .setTo(phoneNumber).create();

        log.info("verificationCheck Info:");
        log.info(verificationCheck + "");

        return verificationCheck.getValid();
    }
}
