package com.sajidtech.easytrip.service;

import com.sajidtech.easytrip.dto.request.DriverRequest;
import com.sajidtech.easytrip.dto.response.BookingResponse;
import com.sajidtech.easytrip.dto.response.DriverResponse;

import java.util.List;

public interface DriverService {

    DriverResponse addDriverInfo(DriverRequest driverRequest);

    DriverResponse getDriverById(int driverId);

    void updateDriverInfo(DriverRequest driverRequest, int driverId);

    List<BookingResponse> getAllBookings(int driverId);

    List<BookingResponse> getAllCompletedBookings(int driverId);

    List<BookingResponse> getAllCancelledBookings(int driverId);

    BookingResponse getAllInProgressBookings(int driverId);

    void deleteDriverById(int driverId);
}
