package com.spring.usermanagement.service;

import com.spring.usermanagement.entity.Role;
import com.spring.usermanagement.entity.User;
import com.spring.usermanagement.mockData.UserMockData;
import com.spring.usermanagement.payload.response.MessageResponse;
import com.spring.usermanagement.repository.RoleRepository;
import com.spring.usermanagement.repository.UserRepository;
import com.spring.usermanagement.security.jwt.JwtUtils;
import javafx.application.Application;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RequiredArgsConstructor
public class UserServiceTest {
    private final UserRepository userRepository = mock(UserRepository.class);
    private final RoleRepository roleRepository = mock(RoleRepository.class);

    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    private UserService userService=new UserService(encoder);

    @Test
    public void should_add_user_successfully(){
        User user = UserMockData.mockUser();
        when(roleRepository.findById(1)).thenReturn(Optional.of(UserMockData.mockUserRole()));
        when(userRepository.save(user)).thenReturn(user);
        assertThat(userService.addUser(user.getUsername(), user.getEmail(), user.getPassword())).isEqualTo(ResponseEntity.ok(new MessageResponse("User registered successfully!")));
    }

    @Test
    public void shoud_add_user_with_duplicate_email_fail(){
        User user = UserMockData.mockUser();
        when(roleRepository.findById(1)).thenReturn(Optional.of(UserMockData.mockUserRole()));
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);
        assertThat(userService.addUser(user.getUsername(), user.getEmail(), user.getPassword())).isEqualTo(ResponseEntity.badRequest().body(new MessageResponse("Error:Email is already in user!")));

    }

    @Test
    public void shoud_add_user_with_duplicate_username_fail(){
        User user = UserMockData.mockUser();
        when(roleRepository.findById(1)).thenReturn(Optional.of(UserMockData.mockUserRole()));
        when(userRepository.existsByUsername(user.getUsername())).thenReturn(true);
        assertThat(userService.addUser(user.getUsername(), user.getEmail(), user.getPassword())).isEqualTo(ResponseEntity.badRequest().body(new MessageResponse("Error:Username is already taken!")));

    }

    @Test
    public void should_update_user_password_successfully(){
        User user = UserMockData.mockUser();
        String newPassword = "123456789";
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        userService.updatePassword(user.getId(), newPassword);

    }

    @Test
    public void should_update_user_active_successfully(){
        User user = UserMockData.mockUser();
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        userService.updateActive(user.getId(),false);
    }
    @Test
    public void should_update_user_role_successfully(){
        User user = UserMockData.mockUser();
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        String role = "admin";
        Set<String> roleList = new HashSet<>();
        roleList.add(role);
        when(roleRepository.findById(3)).thenReturn(Optional.of(UserMockData.mockAdminRole()));
        userService.updateRole(user.getId(),roleList);

    }



}
