package com.tigratius.employeedatastorage.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserVerifyDto {
    private String phoneNumber;
    private String code;
}
