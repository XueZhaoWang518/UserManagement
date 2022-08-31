package com.spring.usermanagement.controller;

import com.spring.usermanagement.entity.User;
import com.spring.usermanagement.payload.request.UpdateActiveRequest;
import com.spring.usermanagement.payload.request.UpdatePasswordRequest;
import com.spring.usermanagement.payload.request.UpdateRoleRequest;
import com.spring.usermanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    //@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public String userAccess() {
        return "Could see the user Content.";
    }

    @GetMapping("/admin/users?page={page}&size={pageSize}")
    @PreAuthorize("hasRole('ADMIN')")
    public Page<User> getUsers(@PathVariable int page, @PathVariable int pageSize) {
        return userService.getUsers(page, pageSize);
    }

    @PostMapping("/update/password")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void updateUserPassword(@Valid @RequestBody UpdatePasswordRequest updatePasswordRequest) {
        userService.updatePassword(updatePasswordRequest.getId(), updatePasswordRequest.getPassword());
    }

    @PostMapping("/moderate/role")
    @PreAuthorize("hasRole('MODERATOR')")
    public void updateUserRole(@Valid @RequestBody UpdateRoleRequest updateRoleRequest) {
        userService.updateRole(updateRoleRequest.getId(), updateRoleRequest.getRoles());
    }

    @PostMapping("/update/active")
    @PreAuthorize("hasRole('ADMIN')")
    public void updateUserActive(@Valid @RequestBody UpdateActiveRequest updateActiveRequest) {
        userService.updateActive(updateActiveRequest.getId(), updateActiveRequest.isActiveStatus());
    }



}
