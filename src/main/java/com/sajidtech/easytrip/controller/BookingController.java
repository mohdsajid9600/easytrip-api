package com.sajidtech.easytrip.controller;

import com.sajidtech.easytrip.dto.request.BookingRequest;
import com.sajidtech.easytrip.dto.response.ApiResponse;
import com.sajidtech.easytrip.dto.response.BookingResponse;
import com.sajidtech.easytrip.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/booking")
public class BookingController {

    // Booking service dependency
    private final BookingService bookingService;

    public BookingController(BookingService bookingService){
        this.bookingService = bookingService;
    }

    // Book a cab for customer
    @PostMapping("/customer/{id}/booked")
    public ResponseEntity<ApiResponse<BookingResponse>> bookCab(@Valid @RequestBody BookingRequest bookingRequest,
                                               @PathVariable("id") int customerId){
        BookingResponse bookingResponse = this.bookingService.bookCab(bookingRequest, customerId);
        return ResponseEntity.ok(ApiResponse.success("Booking created", bookingResponse));
    }

    // Update booked cab details
    @PutMapping("/customer/{id}/update")
    public ResponseEntity<ApiResponse<BookingResponse>> updateBookedDetails(@Valid @RequestBody BookingRequest bookingRequest,
                                                               @PathVariable("id") int customerId){
        BookingResponse bookingResponse = this.bookingService.updateBookedDetails(bookingRequest, customerId);
        return ResponseEntity.ok(ApiResponse.success("Booking updated",bookingResponse));
    }

    // Cancel customer booking
    @PutMapping("/customer/{id}/cancel")
    public ResponseEntity<ApiResponse<String>> cancelBooking(@PathVariable("id") int customerId){
         this.bookingService.cancelBooking(customerId);
        return ResponseEntity.ok(ApiResponse.success("Booking cancelled"));
    }

    // Complete booking by driver
    @PutMapping("/driver/{id}/complete")
    public ResponseEntity<ApiResponse<String>> completeBookingByDriver(@PathVariable("id") int driverId){
        this.bookingService.completeBookingByDriver(driverId);
        return ResponseEntity.ok(ApiResponse.success("Booking completed"));
    }


}
