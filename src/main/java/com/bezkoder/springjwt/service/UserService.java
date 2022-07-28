package com.bezkoder.springjwt.service;

import com.bezkoder.springjwt.entity.Role;
import com.bezkoder.springjwt.entity.User;
import com.bezkoder.springjwt.models.ERole;
import com.bezkoder.springjwt.payload.response.MessageResponse;
import com.bezkoder.springjwt.repository.RoleRepository;
import com.bezkoder.springjwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    public ResponseEntity<?> addUser(String username, String email, String password, Set<String> roles ) {
        if(userRepository.existsByUsername(username)){
            return ResponseEntity.badRequest().body(new MessageResponse("Error:Username is already taken!"));
        }
        if(userRepository.existsByEmail(email)){
            return ResponseEntity.badRequest().body(new MessageResponse("Error:Email is already in user!"));
        }
        Set<Role> roleSet = new HashSet<>();
        if (roles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error:Role is not found."));
            roleSet.add(userRole);
        } else {
            roles.forEach(role->
            {
                switch(role){
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error:Role is not found."));
                        roleSet.add(adminRole);
                        break;

                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error:Role is not found"));
                        roleSet.add(modRole);
                        break;

                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error:Role is not found."));
                        roleSet.add(userRole);

                }
            });
        }
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setRoles(roleSet);
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));


    }
}
