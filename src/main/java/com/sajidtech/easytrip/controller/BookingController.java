package com.sajidtech.easytrip.controller;

import com.sajidtech.easytrip.dto.request.BookingRequest;
import com.sajidtech.easytrip.dto.response.BookingResponse;
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

    @PostMapping("/customer/{id}/booked")
    public ResponseEntity<BookingResponse> bookCab(@RequestBody BookingRequest bookingRequest,
                                  @PathVariable("id") int customerId){
        BookingResponse bookingResponse = bookingService.bookCab(bookingRequest, customerId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(bookingResponse);
    }

    @PutMapping("/customer/{id}/update")
    public ResponseEntity<BookingResponse> updateBookedDetails(@RequestBody BookingRequest bookingRequest,
                                                               @PathVariable("id") int customerId){
        BookingResponse bookingResponse = bookingService.updateBookedDetails(bookingRequest, customerId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(bookingResponse);
    }

    @PutMapping("/customer/{id}/cancel")
    public ResponseEntity<String> cancelBooking(@PathVariable("id") int customerId){
         bookingService.cancelBooking(customerId);
        return ResponseEntity.ok("Your booking has been Cancelled !");
    }

    @PutMapping("/driver/{id}/complete")
    public ResponseEntity<String> completeBookingByDriver(@PathVariable("id") int driverId){
        bookingService.completeBookingByDriver(driverId);
        return ResponseEntity.ok("Booking completed successfully");
    }


}
