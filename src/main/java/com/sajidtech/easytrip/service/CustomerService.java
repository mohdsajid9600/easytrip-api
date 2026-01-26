package com.sajidtech.easytrip.service;

import com.sajidtech.easytrip.dto.request.CustomerRequest;
import com.sajidtech.easytrip.dto.response.BookingResponse;
import com.sajidtech.easytrip.dto.response.CustomerResponse;
import com.sajidtech.easytrip.enums.Gender;

import java.util.List;

public interface CustomerService {

    CustomerResponse createCustomer(CustomerRequest customerRequest);

    CustomerResponse getCustomerById(int customerId);

    List<CustomerResponse> getAllByGenderAndAge(Gender gender, int age);

    List<CustomerResponse> getAllGreaterThenAge(int age);

    void updateCustomerInfo(CustomerRequest customerRequest, int customerId);

    List<BookingResponse> getAllBookings(int customerId);

    List<BookingResponse> getAllCompletedBookings(int customerId);

    List<BookingResponse> getAllCancelledBookings(int customerId);

    BookingResponse getProgressBookings(int customerId);

    void deleteCustomer(int customerId);
}
