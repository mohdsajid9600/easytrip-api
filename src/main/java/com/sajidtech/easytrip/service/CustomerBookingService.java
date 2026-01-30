package com.sajidtech.easytrip.service;

import com.sajidtech.easytrip.dto.request.BookingRequest;
import com.sajidtech.easytrip.dto.response.BookingResponse;


public interface CustomerBookingService extends BookingServiceQueries{

    BookingResponse bookCab(BookingRequest bookingRequest, String email);

    BookingResponse updateBookedDetails(BookingRequest bookingRequest, String email);

    void cancelBooking(String email);
}
