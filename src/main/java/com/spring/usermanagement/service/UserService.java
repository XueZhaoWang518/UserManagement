package com.spring.usermanagement.service;

import com.spring.usermanagement.entity.Role;
import com.spring.usermanagement.entity.User;
import com.spring.usermanagement.entity.ERole;
import com.spring.usermanagement.payload.response.JwtResponse;
import com.spring.usermanagement.payload.response.MessageResponse;
import com.spring.usermanagement.repository.RoleRepository;
import com.spring.usermanagement.repository.UserRepository;
import com.spring.usermanagement.security.jwt.JwtUtils;
import com.spring.usermanagement.security.service.UserDetailsImpls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private PasswordEncoder encoder;

    public ResponseEntity<MessageResponse> addUser(String username, String email, String password, Set<String> roles ) {
        if(userRepository.existsByUsername(username)){
            return ResponseEntity.badRequest().body(new MessageResponse("Error:Username is already taken!"));
        }
        if(userRepository.existsByEmail(email)){
            return ResponseEntity.badRequest().body(new MessageResponse("Error:Email is already in user!"));
        }
        Set<Role> roleSet = new HashSet<>();
        Role role = new Role(ERole.ROLE_USER);
        roleSet.add(role);
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(encoder.encode(password));
        user.setRoles(roleSet);
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));


    }

    public ResponseEntity<JwtResponse> signIn(String user, String password){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpls userDetails = (UserDetailsImpls) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    public ResponseEntity<MessageResponse> updatePassword(String userMessage, String password) {
        if(userRepository.existsByUsername(userMessage)){
            User user = userRepository.findByUsername(userMessage).get();
            user.setPassword(password);
            userRepository.saveAndFlush(user);
            return ResponseEntity.ok(new MessageResponse("User update password successfully!"));
        }
        else if(userRepository.existsByEmail(userMessage)){
            User user = userRepository.findByUsername(userMessage).get();
            user.setPassword(encoder.encode(password));
            userRepository.saveAndFlush(user);
            return ResponseEntity.ok(new MessageResponse("User update password successfully!"));
        }
        else{
            return ResponseEntity.badRequest().body(new MessageResponse("Error:User is not exist!"));
        }

    }

    public ResponseEntity<MessageResponse> updateRole(String userMessage, Set<String> strRoles) {
        Set<Role> roleSet = new HashSet<>();

        if (strRoles == null) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error:Role is not found!"));
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roleSet.add(adminRole);
                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roleSet.add(modRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roleSet.add(userRole);
                }
            });
        }
        if(userRepository.existsByUsername(userMessage)){
            User user = userRepository.findByUsername(userMessage).get();
            user.setRoles(roleSet);
            userRepository.saveAndFlush(user);
            return ResponseEntity.ok(new MessageResponse("User update roles successfully!"));
        }
        else if(userRepository.existsByEmail(userMessage)){
            User user = userRepository.findByUsername(userMessage).get();
            user.setRoles(roleSet);
            userRepository.saveAndFlush(user);
            return ResponseEntity.ok(new MessageResponse("User update roles successfully!"));
        }
        else{
            return ResponseEntity.badRequest().body(new MessageResponse("Error:User is not exist!"));
        }

    }


}
