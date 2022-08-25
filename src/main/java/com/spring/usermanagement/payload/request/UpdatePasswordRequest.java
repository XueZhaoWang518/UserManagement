package com.spring.usermanagement.payload.request;
import lombok.Data;

@Data
public class UpdatePasswordRequest {
    private Long id;
    private String password;
}
