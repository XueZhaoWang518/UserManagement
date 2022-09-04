package com.spring.usermanagement.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SigninRequest {
    private String user;
    private String password;
}
