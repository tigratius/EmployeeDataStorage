package com.tigratius.employeedatastorage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class EmployeeDataStorageApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {

        SpringApplication.run(EmployeeDataStorageApplication.class, args);
    }
}
