package com.sajidtech.easytrip.service;

import com.sajidtech.easytrip.dto.response.*;
import com.sajidtech.easytrip.enums.Gender;

import java.util.List;

public interface AdminService {

    PageResponse<CustomerResponse> getAllCustomer(int page, int size);

    CustomerResponse getCustomerById(int customerId);

    PageResponse<CustomerResponse> getAllByGenderAndAge(Gender gender, int age, int page, int size);

    PageResponse<CustomerResponse> getAllGreaterThenAge(int age, int page, int size);

    PageResponse<CustomerResponse> getActiveCustomers(int page, int size);

    PageResponse<CustomerResponse> getInactiveCustomers(int page, int size);

    CustomerResponse activeCustomer(int customerId);

    CustomerResponse inActiveCustomer(int customerId);

    PageResponse<DriverResponse> getAllDrivers(int page, int size);

    DriverResponse getDriverById(int driverId);

    PageResponse<DriverResponse> getActiveDrivers(int page, int size);

    PageResponse<DriverResponse> getInactiveDrivers(int page, int size);

    DriverResponse activeDriver(int driverId);

    DriverResponse inActiveDriver(int driverId);

    PageResponse<CabResponse> getAllCabs(int page, int size);

    CabResponse getCabById(int cabId);

    PageResponse<CabResponse> getActiveCabs(int page, int size);

    PageResponse<CabResponse> getInactiveCabs(int page, int size);

    PageResponse<CabResponse> getAvailableCabs(int page, int size);

    PageResponse<CabResponse> getUnavailableCabs(int page, int size);

    PageResponse<BookingResponse> getAllBookings(int page, int size);

    BookingResponse getBookingById(int bookingId);

    PageResponse<BookingResponse> getBookingsByCustomer(int customerId,int page, int size);

    PageResponse<BookingResponse> getBookingsByDriver(int driverId,int page, int size);

    PageResponse<BookingResponse> getActiveBookings(int page, int size);

    PageResponse<BookingResponse> getCompletedBookings(int page, int size);

    PageResponse<BookingResponse> getCancelledBookings(int page, int size);


}
