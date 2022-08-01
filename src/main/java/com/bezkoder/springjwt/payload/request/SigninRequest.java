package com.bezkoder.springjwt.payload.request;

import lombok.Data;

@Data
public class SigninRequest {
    private String user;
    private String password;
}
