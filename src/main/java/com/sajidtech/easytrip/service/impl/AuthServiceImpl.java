package com.sajidtech.easytrip.service.impl;

import com.sajidtech.easytrip.dto.request.ChangePasswordRequest;
import com.sajidtech.easytrip.dto.request.LoginRequest;
import com.sajidtech.easytrip.dto.request.SignupRequest;
import com.sajidtech.easytrip.enums.Role;
import com.sajidtech.easytrip.enums.Status;
import com.sajidtech.easytrip.exception.InvalidOldPasswordException;
import com.sajidtech.easytrip.exception.PasswordMismatchException;
import com.sajidtech.easytrip.exception.UserNotFoundException;
import com.sajidtech.easytrip.model.User;
import com.sajidtech.easytrip.repository.UserRepository;
import com.sajidtech.easytrip.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

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

    public String login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(()-> new UsernameNotFoundException("Invalid Email, or User not Found"));

        if(user.getProfileStatus().equals(Status.INACTIVE)){
            throw new RuntimeException("Account is Inactive");
        }

        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                );

        Authentication authentication = authenticationManager.authenticate(token); // real login

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "Login successful";
    }

    public void changePassword(String email, ChangePasswordRequest request) {

        User user = this.userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        // old password match check
        if (!this.passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new InvalidOldPasswordException("Old password is incorrect");
        }

        // 2. New & Confirm password match
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new PasswordMismatchException("New password and confirm password do not match");
        }

        // new password encode + save
        user.setPassword(this.passwordEncoder.encode(request.getNewPassword()));
        this.userRepository.save(user);
    }

}

