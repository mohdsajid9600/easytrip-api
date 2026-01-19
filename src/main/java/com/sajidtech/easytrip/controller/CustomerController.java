package com.sajidtech.easytrip.controller;

import com.sajidtech.easytrip.Enum.Gender;
import com.sajidtech.easytrip.dto.request.CustomerRequest;
import com.sajidtech.easytrip.dto.response.CustomerResponse;
import com.sajidtech.easytrip.model.Customer;
import com.sajidtech.easytrip.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody CustomerRequest customerRequest){
        CustomerResponse customerResponse = customerService.createCustomer(customerRequest);
        // return new ResponseEntity<>(customerResponse, HttpStatus.CREATED);
        if(customerResponse.toString().isEmpty()){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(customerResponse);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(customerResponse);
    }
    @GetMapping("/get-customer/{id}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable("id") int customerId){
        CustomerResponse customerResponse = customerService.getCustomerById(customerId);
        return ResponseEntity.status(HttpStatus.FOUND).body(customerResponse);
    }

    @GetMapping("/getBy-gender-and-age")
    public ResponseEntity<List<CustomerResponse>> getAllByGenderAndAge(@RequestParam("gender") Gender gender,
                                                       @RequestParam("age") int age){
        List<CustomerResponse> responses = customerService.getAllByGenderAndAge(gender, age);
        return ResponseEntity.status(HttpStatus.FOUND).body(responses);
    }

    @GetMapping("/getAll/age-greater-then/{age}")
    public ResponseEntity<List<CustomerResponse>> getAllGreaterThenAge(@PathVariable("age") int age){
        List<CustomerResponse> responses = customerService.getAllGreaterThenAge(age);
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }
    @PutMapping("/update/{customerId}")
    public ResponseEntity<String> updateCustomerInfo(@RequestBody CustomerRequest customerRequest,
                                     @PathVariable("customerId") int customerId){
        boolean isUpdated = customerService.updateCustomerInfo(customerRequest, customerId);
        if(isUpdated){
            return ResponseEntity.status(HttpStatus.OK).body("Your Record updated Successfully!!");
        }
        return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body("Customer not found, Record not updated!");
    }

}
