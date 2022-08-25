package com.spring.usermanagement.payload.response;

public class RoleNotFoundException extends RuntimeException{
    public RoleNotFoundException(){
        super("Could not found role");
    }
}
