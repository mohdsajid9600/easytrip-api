package com.sajidtech.easytrip.transformer;

import com.sajidtech.easytrip.enums.Status;
import com.sajidtech.easytrip.dto.request.CustomerRequest;
import com.sajidtech.easytrip.dto.response.CustomerResponse;
import com.sajidtech.easytrip.model.Customer;

public class CustomerTransformer {

    public static Customer customerRequestToCustomer(CustomerRequest customerRequest){
        return Customer.builder()
                .name(customerRequest.getName())
                .age(customerRequest.getAge())
                .mobileNo(customerRequest.getMobileNo())
                .gender(customerRequest.getGender())
                .status(Status.ACTIVE)
                .build();
    }

    public static CustomerResponse customerToCustomerResponse(Customer customer){
        return CustomerResponse.builder()
                .customerId(customer.getCustomerId())
                .name(customer.getName())
                .age(customer.getAge())
                .email(customer.getEmail())
                .mobileNo(customer.getMobileNo())
                .gender(customer.getGender())
                .status(customer.getStatus())
                .createProfileAt(customer.getCreateProfileAt())
                .lastUpdateAt(customer.getLastUpdateAt())
                .build();
    }

    public static CustomerResponse customerToCustomerResponseSummary(Customer customer){
        return CustomerResponse.builder()
                .name(customer.getName())
                .age(customer.getAge())
                .email(customer.getEmail())
                .mobileNo(customer.getMobileNo())
                .gender(customer.getGender())
                .status(customer.getStatus())
                .createProfileAt(customer.getCreateProfileAt())
                .lastUpdateAt(customer.getLastUpdateAt())
                .build();
    }

    public static CustomerResponse customerToCustomerResponseForDriver(Customer customer){
        return CustomerResponse.builder()
                .name(customer.getName())
                .age(customer.getAge())
                .email(customer.getEmail())
                .mobileNo(customer.getMobileNo())
                .gender(customer.getGender())
                .build();
    }
}
