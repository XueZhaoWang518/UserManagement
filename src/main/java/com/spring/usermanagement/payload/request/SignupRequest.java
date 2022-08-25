package com.spring.usermanagement.payload.request;

import lombok.Data;

import java.util.Set;

@Data
public class SignupRequest {
    private String username;

    private String email;

    private String password;
}

