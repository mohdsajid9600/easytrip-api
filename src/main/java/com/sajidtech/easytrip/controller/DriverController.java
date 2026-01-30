package com.sajidtech.easytrip.controller;

import com.sajidtech.easytrip.dto.request.DriverRequest;
import com.sajidtech.easytrip.dto.response.ApiResponse;
import com.sajidtech.easytrip.dto.response.DriverResponse;
import com.sajidtech.easytrip.service.DriverService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/driver")
public class DriverController {

    // Driver service dependency
    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    // Register new driver
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<DriverResponse>> createProfile(@Valid @RequestBody DriverRequest driverRequest,
                                                                     Principal principal){
        DriverResponse driverResponse = this.driverService.createProfile(driverRequest, principal.getName());
        return ResponseEntity.ok(ApiResponse.success("Driver registered", driverResponse));
    }

    // Get driver by ID
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<DriverResponse>> getProfile(Principal principal){
        DriverResponse driverResponse = this.driverService.getProfile(principal.getName());
        return ResponseEntity.ok(ApiResponse.success("Driver found", driverResponse));
    }

    // Update driver details
    @PutMapping("/me/update")
    public ResponseEntity<ApiResponse<String>> updateProfile(@Valid @RequestBody DriverRequest driverRequest,
                                                   Principal principal){
        this.driverService.updateProfile(driverRequest, principal.getName());
        return ResponseEntity.ok(ApiResponse.success("Driver updated"));
    }

    // Delete driver account
    @DeleteMapping("/me")
    public ResponseEntity<ApiResponse<String>> profileInactive(Principal principal){
        this.driverService.profileInactive(principal.getName());
        return ResponseEntity.ok(ApiResponse.success("Driver Inactive"));
    }
}
