package com.sajidtech.easytrip.controller;

import com.sajidtech.easytrip.dto.request.BookingRequest;
import com.sajidtech.easytrip.dto.response.ApiResponse;
import com.sajidtech.easytrip.dto.response.BookingResponse;
import com.sajidtech.easytrip.dto.response.PageResponse;
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
    public ResponseEntity<ApiResponse<PageResponse<BookingResponse>>> customerBookings(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            Principal principal){
        PageResponse<BookingResponse> responses = this.customerBookingService.getAllBookings(page, size, principal.getName());
        return ResponseEntity.ok(ApiResponse.success("Bookings fetched", responses));
    }

    // Get completed bookings
    @GetMapping("/customer/completed")
    public ResponseEntity<ApiResponse<PageResponse<BookingResponse>>> customerCompletedBookings(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            Principal principal){
        PageResponse<BookingResponse> responses = this.customerBookingService.getAllCompletedBookings(page, size, principal.getName());
        return ResponseEntity.ok(ApiResponse.success("Completed bookings", responses));
    }

    // Get cancelled bookings
    @GetMapping("/customer/cancelled")
    public ResponseEntity<ApiResponse<PageResponse<BookingResponse>>> customerCancelledBookings(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            Principal principal){
        PageResponse<BookingResponse> responses = this.customerBookingService.getAllCancelledBookings(page, size, principal.getName());
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
    public ResponseEntity<ApiResponse<PageResponse<BookingResponse>>> driverBookingsList(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            Principal principal){
        PageResponse<BookingResponse> responses = this.driverBookingService.getAllBookings(page, size, principal.getName());
        return ResponseEntity.ok(ApiResponse.success("Bookings fetched", responses));
    }

    // Get completed bookings
    @GetMapping("/driver/completed")
    public ResponseEntity<ApiResponse<PageResponse<BookingResponse>>> driverCompletedBookingsList(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            Principal principal){
        PageResponse<BookingResponse> responses = this.driverBookingService.getAllCompletedBookings(page, size, principal.getName());
        return ResponseEntity.ok(ApiResponse.success("Completed bookings", responses));
    }

    // Get cancelled bookings
    @GetMapping("/driver/cancelled")
    public ResponseEntity<ApiResponse<PageResponse<BookingResponse>>> driverCancelledBookingsList(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            Principal principal){
        PageResponse<BookingResponse> responses = this.driverBookingService.getAllCancelledBookings(page, size, principal.getName());
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
