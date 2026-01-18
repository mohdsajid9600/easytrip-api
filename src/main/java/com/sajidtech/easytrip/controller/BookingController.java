package com.sajidtech.easytrip.controller;

import com.sajidtech.easytrip.dto.request.BookingRequest;
import com.sajidtech.easytrip.dto.response.BookingResponse;
import com.sajidtech.easytrip.model.Booking;
import com.sajidtech.easytrip.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("bookCab/customer/{customerId}")
    public ResponseEntity<BookingResponse> bookCab(@RequestBody BookingRequest bookingRequest,
                                  @PathVariable("customerId") int customerId){
        BookingResponse bookingResponse = bookingService.bookCab(bookingRequest, customerId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(bookingResponse);
    }
}
