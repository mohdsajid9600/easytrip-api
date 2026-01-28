package com.sajidtech.easytrip.controller;

import com.sajidtech.easytrip.dto.request.LoginRequest;
import com.sajidtech.easytrip.dto.request.SignupRequest;
import com.sajidtech.easytrip.dto.response.ApiResponse;
import com.sajidtech.easytrip.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<String>> signup(@Valid @RequestBody SignupRequest request) {
        String message = authService.register(request);
        return ResponseEntity.ok(ApiResponse.success("User registered successfully", message));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@Valid @RequestBody LoginRequest request) {

        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                );

        Authentication authentication = authenticationManager.authenticate(token); // real login

        SecurityContextHolder.getContext().setAuthentication(authentication);


        return ResponseEntity.ok(ApiResponse.success("Login successful"));
    }








//    @PostMapping("/login")
//    public ResponseEntity<ApiResponse<String>> login(@Valid @RequestBody LoginRequest request) {
//        String message = authService.login(request);
//
//        return ResponseEntity.ok(ApiResponse.success("Login successful", message));
//    }
}
