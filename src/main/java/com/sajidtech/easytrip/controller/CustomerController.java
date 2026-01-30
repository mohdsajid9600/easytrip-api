package com.sajidtech.easytrip.controller;

import com.sajidtech.easytrip.dto.response.ApiResponse;
import com.sajidtech.easytrip.dto.request.CustomerRequest;
import com.sajidtech.easytrip.dto.response.BookingResponse;
import com.sajidtech.easytrip.dto.response.CustomerResponse;
import com.sajidtech.easytrip.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    // Service layer dependency
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }

    // Register new customer
    @PostMapping("/create-profile")
    public ResponseEntity<ApiResponse<CustomerResponse>> createProfile(@Valid @RequestBody CustomerRequest customerRequest, Principal principal){
        CustomerResponse customerResponse = this.customerService.createProfile(customerRequest, principal.getName());
        return ResponseEntity.ok(ApiResponse.success("Customer Registered", customerResponse));
    }

    // Get customer self info
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<CustomerResponse>> getCustomerInfo(Principal principal){
        CustomerResponse customerResponse = this.customerService.getCustomerInfo(principal.getName());
        return ResponseEntity.ok(ApiResponse.success("Customer found", customerResponse));
    }

    // Update customer details
    @PutMapping("/me/update")
    public ResponseEntity<ApiResponse<String>> updateCustomerInfo(@Valid @RequestBody CustomerRequest customerRequest,
                                     Principal principal){
        this.customerService.updateCustomerInfo(customerRequest, principal.getName());
        return ResponseEntity.ok(ApiResponse.success("Customer updated"));
    }

    //In Active customer
    @DeleteMapping("/me")
    public ResponseEntity<ApiResponse<String>> deactivateProfile(Principal principal){
        this.customerService.deactivateProfile(principal.getName());
        return ResponseEntity.ok(ApiResponse.success("Customer INACTIVE right now!"));
    }
}
