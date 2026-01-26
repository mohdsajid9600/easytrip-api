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
import com.sajidtech.easytrip.repository.CustomerRepository;
import com.sajidtech.easytrip.repository.DriverRepository;
import com.sajidtech.easytrip.service.CustomerService;
import com.sajidtech.easytrip.transformer.BookingTransformer;
import com.sajidtech.easytrip.transformer.CustomerTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private DriverRepository driverRepository;


    public CustomerResponse createCustomer(CustomerRequest customerRequest) {
        Customer customer = CustomerTransformer.customerRequestToCustomer(customerRequest);
        Customer savedCustomer = customerRepository.save(customer);
        return CustomerTransformer.customerToCustomerResponse(savedCustomer);
    }

    public CustomerResponse getCustomerById(int customerId) {
        Customer customer = checkValidCustomer(customerId);
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

    public void updateCustomerInfo(CustomerRequest customerRequest, int customerId) {
        Customer customer = checkValidCustomer(customerId);

        customer.setName(customerRequest.getName());
        customer.setAge(customerRequest.getAge());
        customer.setEmail(customerRequest.getEmail());

        customerRepository.save(customer);
    }

    public List<BookingResponse> getAllBookings(int customerId) {
        Customer customer = checkValidCustomer(customerId);
        return customer.getBooking().stream().map(this::getBookingResponseByBooking).toList();
    }

    public List<BookingResponse> getAllCompletedBookings(int customerId) {
        Customer customer = checkValidCustomer(customerId);
        return customer.getBooking().stream()
                .filter(booking -> booking.getTripStatus().equals(TripStatus.COMPLETED))
                .map(this::getBookingResponseByBooking).toList();
    }

    public List<BookingResponse> getAllCancelledBookings(int customerId) {
        Customer customer = checkValidCustomer(customerId);
        return customer.getBooking().stream()
                .filter(booking -> booking.getTripStatus().equals(TripStatus.CANCELLED))
                .map(this::getBookingResponseByBooking).collect(Collectors.toList());
    }

    public BookingResponse getProgressBookings(int customerId) {
        Customer customer = checkValidCustomer(customerId);
        Booking progressBooking = customer.getBooking().stream()
                .filter(booking -> booking.getTripStatus().equals(TripStatus.IN_PROGRESS))
                .findFirst().orElseThrow(()-> new BookingNotFoundException("Customer has no one Booking who are IN_PROGRESS"));
        return getBookingResponseByBooking(progressBooking);
    }

    public void deleteCustomer(int customerId) {
        Customer customer = checkValidCustomer(customerId);
        boolean hasBooking = customer.getBooking().stream().anyMatch(b -> b.getTripStatus().equals(TripStatus.IN_PROGRESS));
        if(hasBooking){
             throw new RuntimeException("Customer have one booking who are IN_PROGRESS");
        }
        customer.setStatus(Status.INACTIVE);
        customerRepository.save(customer);
    }

    private BookingResponse getBookingResponseByBooking(Booking booking) {
        Driver driver = driverRepository.findDriverByBookingId(booking.getBookingId());
        return BookingTransformer.bookingToBookingResponseForCustomer(booking, driver.getCab(), driver);
    }

    private Customer checkValidCustomer(int customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(()-> new CustomerNotFoundException("Customer Id is Invalid with : "+customerId));
        if(customer.getStatus() == Status.INACTIVE){
            throw new RuntimeException("Customer is inactive. Access denied");
        }
        return customer;
    }
}
