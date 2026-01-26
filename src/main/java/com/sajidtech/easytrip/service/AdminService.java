package com.sajidtech.easytrip.service;

import com.sajidtech.easytrip.dto.response.BookingResponse;
import com.sajidtech.easytrip.dto.response.CabResponse;
import com.sajidtech.easytrip.dto.response.CustomerResponse;
import com.sajidtech.easytrip.dto.response.DriverResponse;

import java.util.List;

public interface AdminService {

    List<CustomerResponse> getAllCustomer();

    CustomerResponse getCustomerById(int customerId);

    List<CustomerResponse> getActiveCustomers();

    List<CustomerResponse> getInactiveCustomers();

    CustomerResponse activeCustomer(int customerId);

    CustomerResponse inActiveCustomer(int customerId);

    List<DriverResponse> getAllDrivers();

    DriverResponse getDriverById(int driverId);

    List<DriverResponse> getActiveDrivers();

    List<DriverResponse> getInactiveDrivers();

    DriverResponse activeDriver(int driverId);

    DriverResponse inActiveDriver(int driverId);

    List<CabResponse> getAllCabs();

    CabResponse getCabById(int cabId);

    List<CabResponse> getActiveCabs();

    List<CabResponse> getInactiveCabs();

    List<CabResponse> getAvailableCabs();

    List<CabResponse> getUnavailableCabs();

    List<BookingResponse> getAllBookings();

    BookingResponse getBookingById(int bookingId);

    List<BookingResponse> getBookingsByCustomer(int customerId);

    List<BookingResponse> getBookingsByDriver(int driverId);

    List<BookingResponse> getActiveBookings();

    List<BookingResponse> getCompletedBookings();

    List<BookingResponse> getCancelledBookings();


}
