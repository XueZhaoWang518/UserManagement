package com.bezkoder.springjwt.controller;

import com.bezkoder.springjwt.payload.request.SignupRequest;
import com.bezkoder.springjwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?>  registerUser(@Valid @RequestBody SignupRequest signupRequest)
    {
        return userService.addUser(signupRequest.getUsername(), signupRequest.getEmail(), signupRequest.getPassword(), signupRequest.getRoles());
    }


}
