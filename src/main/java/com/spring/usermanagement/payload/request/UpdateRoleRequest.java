package com.spring.usermanagement.payload.request;

import lombok.Data;

import java.util.Set;
@Data
public class UpdateRoleRequest {
    private Long id;

    private Set<String> roles;
}
