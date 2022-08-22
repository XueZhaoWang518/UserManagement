package com.spring.usermanagement.controller;

import com.spring.usermanagement.entity.User;
import com.spring.usermanagement.payload.request.SigninRequest;
import com.spring.usermanagement.payload.request.UpdateRoleRequest;
import com.spring.usermanagement.repository.UserRepository;
import com.spring.usermanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/all")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public String userAccess() {
        return "Could see the user Content.";
    }

    @PostMapping("/update/password")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> updateUserPassword(@Valid @RequestBody SigninRequest signinRequest) {
        return userService.updatePassword(signinRequest.getUser(), signinRequest.getPassword());
    }

    @PostMapping("/moderate/role")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<?> updateUserRole(@Valid @RequestBody UpdateRoleRequest updateRoleRequest) {
        return userService.updateRole(updateRoleRequest.getId(), updateRoleRequest.getRoles());
    }




}
