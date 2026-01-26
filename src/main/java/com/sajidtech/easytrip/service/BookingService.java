package com.sajidtech.easytrip.service;

import com.sajidtech.easytrip.dto.request.BookingRequest;
import com.sajidtech.easytrip.dto.response.BookingResponse;

public interface BookingService {

    BookingResponse bookCab(BookingRequest bookingRequest, int customerId);

    BookingResponse updateBookedDetails(BookingRequest bookingRequest, int customerId);

    void cancelBooking(int customerId);

    void completeBookingByDriver(int driverId);
}
