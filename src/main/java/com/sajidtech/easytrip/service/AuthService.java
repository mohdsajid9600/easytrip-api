package com.sajidtech.easytrip.service;

import com.sajidtech.easytrip.dto.request.ChangePasswordRequest;
import com.sajidtech.easytrip.dto.request.LoginRequest;
import com.sajidtech.easytrip.dto.request.SignupRequest;
import com.sajidtech.easytrip.dto.response.UserResponse;
import jakarta.validation.Valid;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {

    String register(SignupRequest request);

    String login(LoginRequest request, HttpServletRequest httpRequest, HttpServletResponse httpResponse);

    void changePassword(String name, @Valid ChangePasswordRequest request);

    UserResponse getCurrentUser(String email);
}
