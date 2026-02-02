package com.sajidtech.easytrip.service;

import com.sajidtech.easytrip.dto.response.BookingResponse;
import com.sajidtech.easytrip.dto.response.PageResponse;

import java.util.List;

public interface BookingServiceQueries {

    PageResponse<BookingResponse> getAllBookings(int page, int size, String email);

    PageResponse<BookingResponse> getAllCompletedBookings(int page, int size, String email);

    PageResponse<BookingResponse> getAllCancelledBookings(int page, int size, String email);

    BookingResponse getProgressBookings(String email);

}
