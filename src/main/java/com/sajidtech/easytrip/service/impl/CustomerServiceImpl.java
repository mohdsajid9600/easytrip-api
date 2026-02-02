package com.sajidtech.easytrip.service.impl;

import com.sajidtech.easytrip.enums.Gender;
import com.sajidtech.easytrip.enums.Status;
import com.sajidtech.easytrip.enums.TripStatus;
import com.sajidtech.easytrip.dto.request.CustomerRequest;
import com.sajidtech.easytrip.dto.response.BookingResponse;
import com.sajidtech.easytrip.dto.response.CustomerResponse;
import com.sajidtech.easytrip.exception.BookingNotFoundException;
import com.sajidtech.easytrip.exception.CustomerNotFoundException;
import com.sajidtech.easytrip.model.Booking;
import com.sajidtech.easytrip.model.Customer;
import com.sajidtech.easytrip.model.Driver;
import com.sajidtech.easytrip.model.User;
import com.sajidtech.easytrip.repository.CustomerRepository;
import com.sajidtech.easytrip.repository.DriverRepository;
import com.sajidtech.easytrip.repository.UserRepository;
import com.sajidtech.easytrip.service.CustomerService;
import com.sajidtech.easytrip.transformer.BookingTransformer;
import com.sajidtech.easytrip.transformer.CustomerTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserRepository userRepository;


    @Transactional
    public CustomerResponse createProfile(CustomerRequest customerRequest, String email) {
        User user = this.userRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));
        if(user.getProfileStatus().equals(Status.INACTIVE)){
            throw new RuntimeException("Customer is inactive. Access denied");
        }
        Customer customer = CustomerTransformer.customerRequestToCustomer(customerRequest);
        customer.setEmail(user.getEmail());
        customer.setUser(user);
        Customer savedCustomer = this.customerRepository.save(customer);
        return CustomerTransformer.customerToCustomerResponse(savedCustomer);
    }

    public CustomerResponse getCustomerInfo(String email) {
        Customer customer = checkValidCustomer(email);
        return CustomerTransformer.customerToCustomerResponse(customer);
    }

    public void updateCustomerInfo(CustomerRequest customerRequest, String email) {
        Customer customer = checkValidCustomer(email);

        customer.setName(customerRequest.getName());
        customer.setAge(customerRequest.getAge());
        customer.setGender(customerRequest.getGender());

        this.customerRepository.save(customer);
    }

    @Transactional
    public void deactivateProfile(String email) {
        Customer customer = checkValidCustomer(email);
        boolean hasBooking = customer.getBooking().stream().anyMatch(b -> b.getTripStatus().equals(TripStatus.IN_PROGRESS));
        if(hasBooking){
             throw new RuntimeException("Do complete/Cancel to the pending booking");
        }
        customer.setStatus(Status.INACTIVE);
        customer.getUser().setProfileStatus(Status.INACTIVE);
        this.customerRepository.save(customer);
    }

    private Customer checkValidCustomer(String email) {
        Customer customer = this.customerRepository.findByEmail(email)
                .orElseThrow(()-> new CustomerNotFoundException("Customer Not Found"));
        if(customer.getStatus() == Status.INACTIVE){
            throw new RuntimeException("Customer is inactive. Access denied");
        }
        return customer;
    }
}
