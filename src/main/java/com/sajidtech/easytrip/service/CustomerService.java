package com.sajidtech.easytrip.service;

import com.sajidtech.easytrip.Enum.Gender;
import com.sajidtech.easytrip.Enum.TripStatus;
import com.sajidtech.easytrip.dto.request.CustomerRequest;
import com.sajidtech.easytrip.dto.response.BookingResponse;
import com.sajidtech.easytrip.dto.response.CabResponse;
import com.sajidtech.easytrip.dto.response.CustomerResponse;
import com.sajidtech.easytrip.exception.BookingNotFound;
import com.sajidtech.easytrip.exception.CustomerNotFoundException;
import com.sajidtech.easytrip.model.Booking;
import com.sajidtech.easytrip.model.Cab;
import com.sajidtech.easytrip.model.Customer;
import com.sajidtech.easytrip.model.Driver;
import com.sajidtech.easytrip.repository.CabRepository;
import com.sajidtech.easytrip.repository.CustomerRepository;
import com.sajidtech.easytrip.repository.DriverRepository;
import com.sajidtech.easytrip.transformer.BookingTransformer;
import com.sajidtech.easytrip.transformer.CabTransformer;
import com.sajidtech.easytrip.transformer.CustomerTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private DriverRepository driverRepository;


    public CustomerResponse createCustomer(CustomerRequest customerRequest) {
        Customer customer = CustomerTransformer.customerRequestToCustomer(customerRequest);
        Customer savedCustomer = customerRepository.save(customer);
        return CustomerTransformer.customerToCustomerResponse(savedCustomer);
    }

    public CustomerResponse getCustomerById(int id) {
        Customer customer = customerRepository.findById(id).orElseThrow(()-> new CustomerNotFoundException("Customer Id is Invalid with : "+id));
        return CustomerTransformer.customerToCustomerResponse(customer);
    }

    public List<CustomerResponse> getAllByGenderAndAge(Gender gender, int age) {
        List<Customer> customers = customerRepository.findByGenderAndAge(gender, age);
       return customers.stream().map(CustomerTransformer::customerToCustomerResponse).collect(Collectors.toList());
    }

    public List<CustomerResponse> getAllGreaterThenAge(int age) {
        List<Customer> customers = customerRepository.getAllGreaterThenAge(age);
        return customers.stream().map(CustomerTransformer::customerToCustomerResponse).collect(Collectors.toList());
    }

    public Boolean updateCustomerInfo(CustomerRequest customerRequest, int customerId) {
        Optional<Customer> OptCustomer = customerRepository.findById(customerId);
        if(OptCustomer.isEmpty()){
            return false;
        }
        Customer customer = OptCustomer.get();

        customer.setName(customerRequest.getName());
        customer.setAge(customerRequest.getAge());
        customer.setEmail(customerRequest.getEmail());

        customerRepository.save(customer);
        return true;
    }

    public List<BookingResponse> getAllBookings(int customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(()-> new CustomerNotFoundException("Customer Id is Invalid"));

        return customer.getBooking().stream().map(booking -> {

            Driver driver = driverRepository.findByBookingId(booking.getBookingId());
            return BookingTransformer.bookingToBookingResponseForCustomer(booking, driver.getCab(), driver);

        }).collect(Collectors.toList());

    }

    public List<BookingResponse> getAllCompletedBookings(int customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(()-> new CustomerNotFoundException("Customer Id is Invalid"));

        return customer.getBooking().stream().filter(booking -> booking.getTripStatus().equals(TripStatus.COMPLETED)).map(booking -> {

            Driver driver = driverRepository.findByBookingId(booking.getBookingId());
            return BookingTransformer.bookingToBookingResponseForCustomer(booking, driver.getCab(), driver);

        }).collect(Collectors.toList());

    }

    public List<BookingResponse> getAllCancelledBookings(int customerId) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(()-> new CustomerNotFoundException("Customer Id is Invalid"));

        return customer.getBooking().stream().filter(booking -> booking.getTripStatus().equals(TripStatus.CANCELLED)).map(booking -> {

            Driver driver = driverRepository.findByBookingId(booking.getBookingId());
            return BookingTransformer.bookingToBookingResponseForCustomer(booking, driver.getCab(), driver);

        }).collect(Collectors.toList());
    }

    public BookingResponse getProgressBookings(int customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(()-> new CustomerNotFoundException("Customer Id is Invalid"));
        Booking progressBooking = customer.getBooking().stream().filter(booking -> booking.getTripStatus().equals(TripStatus.IN_PROGRESS))
                .findFirst().orElseThrow(()-> new BookingNotFound("Customer has no one Booking who is IN_PROGRESS"));

        Driver driver = driverRepository.findByBookingId(progressBooking.getBookingId());
        return BookingTransformer.bookingToBookingResponseForCustomer(progressBooking,driver.getCab(), driver);
    }


}
