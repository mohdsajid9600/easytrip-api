package com.sajidtech.easytrip.service;

import com.sajidtech.easytrip.Enum.Gender;
import com.sajidtech.easytrip.dto.request.CustomerRequest;
import com.sajidtech.easytrip.dto.response.CustomerResponse;
import com.sajidtech.easytrip.exception.CustomerNotFoundException;
import com.sajidtech.easytrip.model.Customer;
import com.sajidtech.easytrip.repository.CustomerRepository;
import com.sajidtech.easytrip.transformer.CustomerTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public CustomerResponse createCustomer(CustomerRequest customerRequest) {
        Customer customer = CustomerTransformer.customerRequestToCustomer(customerRequest);
        Customer savedCustomer = customerRepository.save(customer);
        return CustomerTransformer.CustomerToCustomerResponse(savedCustomer);
    }

    public CustomerResponse getCustomerById(int id) {
        Customer customer = customerRepository.findById(id).orElseThrow(()-> new CustomerNotFoundException("Customer Id is Invalid with : "+id));
        return CustomerTransformer.CustomerToCustomerResponse(customer);
    }

    public List<CustomerResponse> getAllByGenderAndAge(Gender gender, int age) {
        List<Customer> customers = customerRepository.findByGenderAndAge(gender, age);
       return customers.stream().map((customer) -> CustomerTransformer.CustomerToCustomerResponse(customer)).collect(Collectors.toList());
    }

    public List<CustomerResponse> getAllGreaterThenAge(int age) {
        List<Customer> customers = customerRepository.getAllGreaterThenAge(age);
        return customers.stream().map((customer) -> CustomerTransformer.CustomerToCustomerResponse(customer)).collect(Collectors.toList());
    }
}
