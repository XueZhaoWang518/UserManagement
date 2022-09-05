package com.spring.usermanagement.controller;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.spring.usermanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.spring.usermanagement.payload.request.SigninRequest;
import com.spring.usermanagement.payload.request.SignupRequest;
import com.spring.usermanagement.payload.response.JwtResponse;
import com.spring.usermanagement.payload.response.MessageResponse;
import com.spring.usermanagement.security.jwt.JwtUtils;

import com.spring.usermanagement.security.service.UserDetailsImpls;


@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody SigninRequest loginRequest) {
        return userService.signIn(loginRequest.getUser(), loginRequest.getPassword());
    }

    @PostMapping("/signup")
    public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        System.out.println(signUpRequest);
        return userService.addUser(signUpRequest.getUsername(), signUpRequest.getEmail(), signUpRequest.getPassword());
    }

//    @PostMapping("/resetPassword")
//    public ResponseEntity<MessageResponse>  resetPassword(HttpServletRequest request, @RequestParam("email") String userEmail){
//        return userService.resetPassword(request, userEmail);
//
//    }
}

