package com.sajidtech.easytrip.controller;

import com.sajidtech.easytrip.dto.request.BookingRequest;
import com.sajidtech.easytrip.dto.response.ApiResponse;
import com.sajidtech.easytrip.dto.response.BookingResponse;
import com.sajidtech.easytrip.service.CustomerBookingService;
import com.sajidtech.easytrip.service.DriverBookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/booking")
@RequiredArgsConstructor
public class BookingController {

    // Booking service dependency
    private final CustomerBookingService customerBookingService;

    private final DriverBookingService driverBookingService;


//________________________________________________CUSTOMER SECTION______________________________________________________________

    // Book a cab for customer
    @PostMapping("/customer/booked")
    public ResponseEntity<ApiResponse<BookingResponse>> bookCab(@Valid @RequestBody BookingRequest bookingRequest,
                                               Principal principal){
        BookingResponse bookingResponse = this.customerBookingService.bookCab(bookingRequest, principal.getName());
        return ResponseEntity.ok(ApiResponse.success("Booking created", bookingResponse));
    }

    // Get all bookings of customer
    @GetMapping("/customer")
    public ResponseEntity<ApiResponse<List<BookingResponse>>> customerBookings(Principal principal){
        List<BookingResponse> responses = this.customerBookingService.getAllBookings(principal.getName());
        return ResponseEntity.ok(ApiResponse.success("Bookings fetched", responses));
    }

    // Get completed bookings
    @GetMapping("/customer/completed")
    public ResponseEntity<ApiResponse<List<BookingResponse>>> customerCompletedBookings(Principal principal){
        List<BookingResponse> responses = this.customerBookingService.getAllCompletedBookings(principal.getName());
        return ResponseEntity.ok(ApiResponse.success("Completed bookings", responses));
    }

    // Get cancelled bookings
    @GetMapping("/customer/cancelled")
    public ResponseEntity<ApiResponse<List<BookingResponse>>> customerCancelledBookings(Principal principal){
        List<BookingResponse> responses = this.customerBookingService.getAllCancelledBookings(principal.getName());
        return ResponseEntity.ok(ApiResponse.success("Cancelled bookings", responses));
    }

    // Get in-progress booking
    @GetMapping("/customer/active")
    public ResponseEntity<ApiResponse<BookingResponse>> customerActiveBookings(Principal principal){
        BookingResponse response = this.customerBookingService.getProgressBookings(principal.getName());
        return ResponseEntity.ok(ApiResponse.success("Active booking", response));
    }

    // Update booked cab details
    @PutMapping("/customer/update")
    public ResponseEntity<ApiResponse<BookingResponse>> customerBookingUpdate(@Valid @RequestBody BookingRequest bookingRequest,
                                                               Principal principal){
        BookingResponse bookingResponse = this.customerBookingService.updateBookedDetails(bookingRequest, principal.getName());
        return ResponseEntity.ok(ApiResponse.success("Booking updated",bookingResponse));
    }

    // Cancel customer booking
    @PutMapping("/customer/cancel")
    public ResponseEntity<ApiResponse<String>> customerBookingCancel(Principal principal){
         this.customerBookingService.cancelBooking(principal.getName());
        return ResponseEntity.ok(ApiResponse.success("Booking cancelled"));
    }

//________________________________________________DRIVER SECTION______________________________________________________________

    // Get all driver bookings
    @GetMapping("/driver")
    public ResponseEntity<ApiResponse<List<BookingResponse>>> driverBookingsList(Principal principal){
        List<BookingResponse> responses = this.driverBookingService.getAllBookings(principal.getName());
        return ResponseEntity.ok(ApiResponse.success("Bookings fetched", responses));
    }

    // Get completed bookings
    @GetMapping("/driver/completed")
    public ResponseEntity<ApiResponse<List<BookingResponse>>> driverCompletedBookingsList(Principal principal){
        List<BookingResponse> responses = this.driverBookingService.getAllCompletedBookings(principal.getName());
        return ResponseEntity.ok(ApiResponse.success("Completed bookings", responses));
    }

    // Get cancelled bookings
    @GetMapping("/driver/cancelled")
    public ResponseEntity<ApiResponse<List<BookingResponse>>> driverCancelledBookingsList(Principal principal){
        List<BookingResponse> responses = this.driverBookingService.getAllCancelledBookings(principal.getName());
        return ResponseEntity.ok(ApiResponse.success("Cancelled bookings", responses));
    }

    // Get in-progress bookings
    @GetMapping("/driver/active")
    public ResponseEntity<ApiResponse<BookingResponse>> driverActiveBooking(Principal principal){
        BookingResponse response = this.driverBookingService.getProgressBookings(principal.getName());
        return ResponseEntity.ok(ApiResponse.success("Active bookings",response));
    }

    // Complete booking by driver
    @PutMapping("/driver/complete")
    public ResponseEntity<ApiResponse<String>> driverBookingComplete(Principal principal){
        this.driverBookingService.completeBookingByDriver(principal.getName());
        return ResponseEntity.ok(ApiResponse.success("Booking completed"));
    }


}
