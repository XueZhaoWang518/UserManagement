package com.spring.usermanagement.payload.response;

public class GenericResponse {
    private String message;
    private String error;

    public GenericResponse(String message, String error) {
        super();
        this.message = message;
        this.error = error;
    }

    public GenericResponse(String message) {
        super();
        this.message = message;
    }
}
