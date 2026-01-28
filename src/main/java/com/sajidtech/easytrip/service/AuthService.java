package com.sajidtech.easytrip.service;

import com.sajidtech.easytrip.dto.request.LoginRequest;
import com.sajidtech.easytrip.dto.request.SignupRequest;
import com.sajidtech.easytrip.enums.Role;
import com.sajidtech.easytrip.enums.Status;
import com.sajidtech.easytrip.model.User;
import com.sajidtech.easytrip.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String register(SignupRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        // Can't any one Signup with ADMIN Role
        if (request.getRole() == Role.ADMIN) {
            throw new RuntimeException("Admin signup is not allowed");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        user.setProfileStatus(Status.ACTIVE);

        userRepository.save(user);

        return "User registered successfully";
    }

//    public String login(LoginRequest request) {
//
//        User user = userRepository.findByEmail(request.getEmail())
//                .orElseThrow(() -> new RuntimeException("Invalid email"));
//
//        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
//            throw new RuntimeException("Invalid password");
//        }
//
//        return "Login successful";
//    }
}

