package com.sajidtech.easytrip.service;

import com.sajidtech.easytrip.dto.request.ChangePasswordRequest;
import com.sajidtech.easytrip.dto.request.LoginRequest;
import com.sajidtech.easytrip.dto.request.SignupRequest;
import jakarta.validation.Valid;

public interface AuthService {

    String register(SignupRequest request);

    String login(LoginRequest request);

    void changePassword(String name, @Valid ChangePasswordRequest request);
}
