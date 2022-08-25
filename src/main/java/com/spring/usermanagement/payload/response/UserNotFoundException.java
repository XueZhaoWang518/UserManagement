package com.spring.usermanagement.payload.response;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(){
        super("Could not found user");
    }
}
