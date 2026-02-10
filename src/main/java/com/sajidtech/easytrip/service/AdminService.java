package com.sajidtech.easytrip.service;

import com.sajidtech.easytrip.dto.response.*;
import com.sajidtech.easytrip.enums.Gender;


public interface AdminService {

    PageResponse<CustomerResponse> getAllCustomer(Integer page, Integer size);

    CustomerResponse getCustomerById(Integer customerId);

    PageResponse<CustomerResponse> getAllByGenderAndAge(Gender gender, Byte age, Integer page, Integer size);

    PageResponse<CustomerResponse> getAllGreaterThenAge(Byte age, Integer page, Integer size);

    PageResponse<CustomerResponse> getActiveCustomers(Integer page, Integer size);

    PageResponse<CustomerResponse> getInactiveCustomers(Integer page, Integer size);

    CustomerResponse activeCustomer(Integer customerId);

    CustomerResponse inActiveCustomer(Integer customerId);

    PageResponse<DriverResponse> getAllDrivers(Integer page, Integer size);

    DriverResponse getDriverById(Integer driverId);

    PageResponse<DriverResponse> getActiveDrivers(Integer page, Integer size);

    PageResponse<DriverResponse> getInactiveDrivers(Integer page, Integer size);

    DriverResponse activeDriver(Integer driverId);

    DriverResponse inActiveDriver(Integer driverId);

    PageResponse<CabResponse> getAllCabs(Integer page, Integer size);

    CabResponse getCabById(Integer cabId);

    PageResponse<CabResponse> getActiveCabs(Integer page, Integer size);

    PageResponse<CabResponse> getInactiveCabs(Integer page, Integer size);

    PageResponse<CabResponse> getAvailableCabs(Integer page, Integer size);

    PageResponse<CabResponse> getUnavailableCabs(Integer page, Integer size);

    PageResponse<BookingResponse> getAllBookings(Integer page, Integer size);

    BookingResponse getBookingById(Integer bookingId);

    PageResponse<BookingResponse> getBookingsByCustomer(Integer customerId,Integer page, Integer size);

    PageResponse<BookingResponse> getBookingsByDriver(Integer driverId,Integer page, Integer size);

    PageResponse<BookingResponse> getActiveBookings(Integer page, Integer size);

    PageResponse<BookingResponse> getCompletedBookings(Integer page, Integer size);

    PageResponse<BookingResponse> getCancelledBookings(Integer page, Integer size);


}
