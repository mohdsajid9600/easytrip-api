package com.sajidtech.easytrip.transformer;

import com.sajidtech.easytrip.Enum.Status;
import com.sajidtech.easytrip.dto.request.CustomerRequest;
import com.sajidtech.easytrip.dto.response.CustomerResponse;
import com.sajidtech.easytrip.model.Customer;

public class CustomerTransformer {

    public static Customer customerRequestToCustomer(CustomerRequest customerRequest){
        return Customer.builder()
                .name(customerRequest.getName())
                .age(customerRequest.getAge())
                .email(customerRequest.getEmail())
                .gender(customerRequest.getGender())
                .status(Status.ACTIVE)
                .build();
    }

    public static CustomerResponse customerToCustomerResponse(Customer customer){
        return CustomerResponse.builder()
                .name(customer.getName())
                .age(customer.getAge())
                .email(customer.getEmail())
                .gender(customer.getGender())
                .status(customer.getStatus())
                .build();
    }

    public static CustomerResponse customerToCustomerResponseForDriver(Customer customer){
        return CustomerResponse.builder()
                .name(customer.getName())
                .age(customer.getAge())
                .email(customer.getEmail())
                .gender(customer.getGender())
                .build();
    }
}
