package com.sajidtech.easytrip.controller;

import com.sajidtech.easytrip.Enum.Gender;
import com.sajidtech.easytrip.dto.request.CustomerRequest;
import com.sajidtech.easytrip.dto.response.BookingResponse;
import com.sajidtech.easytrip.dto.response.CustomerResponse;
import com.sajidtech.easytrip.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/register/customer")
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody CustomerRequest customerRequest){
        CustomerResponse customerResponse = customerService.createCustomer(customerRequest);
        // return new ResponseEntity<>(customerResponse, HttpStatus.CREATED);
        return ResponseEntity.status(HttpStatus.CREATED).body(customerResponse);
    }

    @GetMapping("/customer")
    public ResponseEntity<CustomerResponse> getCustomerById(@RequestParam("id") int customerId){
        CustomerResponse customerResponse = customerService.getCustomerById(customerId);
        return ResponseEntity.status(HttpStatus.FOUND).body(customerResponse);
    }

    @GetMapping("/search")
    public ResponseEntity<List<CustomerResponse>> getAllByGenderAndAge(@RequestParam("gender") Gender gender,
                                                       @RequestParam("age") int age){
        List<CustomerResponse> responses = customerService.getAllByGenderAndAge(gender, age);
        return ResponseEntity.status(HttpStatus.FOUND).body(responses);
    }

    @GetMapping("/search/greater")
    public ResponseEntity<List<CustomerResponse>> getAllGreaterThenAge(@RequestParam("age") int age){
        List<CustomerResponse> responses = customerService.getAllGreaterThenAge(age);
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @PutMapping("/customer/{id}/update")
    public ResponseEntity<String> updateCustomerInfo(@RequestBody CustomerRequest customerRequest,
                                     @PathVariable("id") int customerId){
        customerService.updateCustomerInfo(customerRequest, customerId);
        return ResponseEntity.status(HttpStatus.OK).body("Your Record updated Successfully!!");
    }

    @GetMapping("/customer/{id}/bookings")
    public ResponseEntity<List<BookingResponse>> getAllBookings(@PathVariable("id") int customerId){
        List<BookingResponse> responses = customerService.getAllBookings(customerId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/customer/{id}/bookings/completed")
    public ResponseEntity<List<BookingResponse>> getAllCompletedBookings(@PathVariable("id") int customerId){
        List<BookingResponse> responses = customerService.getAllCompletedBookings(customerId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/customer/{id}/bookings/cancelled")
    public ResponseEntity<List<BookingResponse>> getAllCancelledBookings(@PathVariable("id") int customerId){
        List<BookingResponse> responses = customerService.getAllCancelledBookings(customerId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/customer/{id}/bookings/in-progress")
    public ResponseEntity<BookingResponse> getProgressBookings(@PathVariable("id") int customerId){
        BookingResponse response = customerService.getProgressBookings(customerId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/customer/{id}/delete")
    public ResponseEntity<String> deleteCustomer(@PathVariable("id") int customerId){
        customerService.deleteCustomer(customerId);
        return ResponseEntity.ok("Customer delete successfully!");
    }
}
