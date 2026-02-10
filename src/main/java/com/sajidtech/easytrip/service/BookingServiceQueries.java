package com.sajidtech.easytrip.service;

import com.sajidtech.easytrip.dto.response.BookingResponse;
import com.sajidtech.easytrip.dto.response.PageResponse;

import java.util.List;

public interface BookingServiceQueries {

    PageResponse<BookingResponse> getAllBookings(Integer page, Integer size, String email);

    PageResponse<BookingResponse> getAllCompletedBookings(Integer page, Integer size, String email);

    PageResponse<BookingResponse> getAllCancelledBookings(Integer page, Integer size, String email);

    BookingResponse getProgressBookings(String email);

}
