package com.sajidtech.easytrip.controller;

import com.sajidtech.easytrip.dto.request.ChangePasswordRequest;
import com.sajidtech.easytrip.dto.request.LoginRequest;
import com.sajidtech.easytrip.dto.request.SignupRequest;
import com.sajidtech.easytrip.dto.response.ApiResponse;
import com.sajidtech.easytrip.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<String>> signup(@Valid @RequestBody SignupRequest request) {
        String message = this.authService.register(request);
        return ResponseEntity.ok(ApiResponse.success("User registered successfully", message));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@Valid @RequestBody LoginRequest request) {

        String message = this.authService.login(request);
        return ResponseEntity.ok(ApiResponse.success(message));
    }

    @PostMapping("/change-password")
    public ResponseEntity<ApiResponse<String>> changePassword(
            @Valid @RequestBody ChangePasswordRequest request,
            Principal principal, HttpServletRequest httpServletRequest)
    {

        this.authService.changePassword(principal.getName(), request);
        //User auto logout
        httpServletRequest.getSession().invalidate();            // session destroy
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok(
                ApiResponse.success("Password changed successfully")
        );
    }

}
