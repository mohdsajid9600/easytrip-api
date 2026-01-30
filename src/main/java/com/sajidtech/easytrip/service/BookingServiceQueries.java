package com.sajidtech.easytrip.service;

import com.sajidtech.easytrip.dto.response.BookingResponse;

import java.util.List;

public interface BookingServiceQueries {

    List<BookingResponse> getAllBookings(String email);

    List<BookingResponse> getAllCompletedBookings(String email);

    List<BookingResponse> getAllCancelledBookings(String email);

    BookingResponse getProgressBookings(String email);

}
