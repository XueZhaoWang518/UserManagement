package com.spring.usermanagement.payload.request;

import lombok.Data;

import java.util.Set;
@Data
public class UpdateRoleRequest {
    private String user;

    private Set<String> roles;
}
