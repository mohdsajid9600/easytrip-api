package com.sajidtech.easytrip.controller;

import com.sajidtech.easytrip.dto.request.DriverRequest;
import com.sajidtech.easytrip.dto.response.ApiResponse;
import com.sajidtech.easytrip.dto.response.BookingResponse;
import com.sajidtech.easytrip.dto.response.DriverResponse;
import com.sajidtech.easytrip.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/drivers")
public class DriverController {

    // Driver service dependency
    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    // Register new driver
    @PostMapping("/register/driver")
    public ResponseEntity<ApiResponse<DriverResponse>> addDriverInfo(@RequestBody DriverRequest driverRequest){
        DriverResponse driverResponse = this.driverService.addDriverInfo(driverRequest);
        return ResponseEntity.ok(ApiResponse.success("Driver registered", driverResponse));
    }

    // Get driver by ID
    @GetMapping("/driver")
    public ResponseEntity<ApiResponse<DriverResponse>> getDriverById(@RequestParam("id") int id){
        DriverResponse driverResponse = this.driverService.getDriverById(id);
        return ResponseEntity.ok(ApiResponse.success("Driver found", driverResponse));
    }

    // Update driver details
    @PutMapping("/driver/{id}/update")
    public ResponseEntity<ApiResponse<String>> updateDriverInfo(@RequestBody DriverRequest driverRequest,
                                                   @PathVariable("id") int driverId){
        this.driverService.updateDriverInfo(driverRequest, driverId);
        return ResponseEntity.ok(ApiResponse.success("Driver updated"));
    }

    // Get all driver bookings
    @GetMapping("/driver/{id}/bookings")
    public ResponseEntity<ApiResponse<List<BookingResponse>>> getAllBookings(@PathVariable("id") int driverId){
        List<BookingResponse> responses = this.driverService.getAllBookings(driverId);
        return ResponseEntity.ok(ApiResponse.success("Bookings fetched", responses));
    }

    // Get completed bookings
    @GetMapping("/driver/{id}/bookings/completed")
    public ResponseEntity<ApiResponse<List<BookingResponse>>> getAllCompletedBookings(@PathVariable("id") int driverId){
        List<BookingResponse> responses = this.driverService.getAllCompletedBookings(driverId);
        return ResponseEntity.ok(ApiResponse.success("Completed bookings", responses));
    }

    // Get cancelled bookings
    @GetMapping("/driver/{id}/bookings/cancelled")
    public ResponseEntity<ApiResponse<List<BookingResponse>>> getAllCancelledBookings(@PathVariable("id") int driverId){
        List<BookingResponse> responses = this.driverService.getAllCancelledBookings(driverId);
        return ResponseEntity.ok(ApiResponse.success("Cancelled bookings", responses));
    }

    // Get in-progress bookings
    @GetMapping("/driver/{id}/bookings/in-progress")
    public ResponseEntity<ApiResponse<BookingResponse>> getAllInProgressBookings(@PathVariable("id") int driverId){
        BookingResponse response = this.driverService.getAllInProgressBookings(driverId);
        return ResponseEntity.ok(ApiResponse.success("Active bookings",response));
    }

    // Delete driver account
    @DeleteMapping("/driver/{id}")
    public ResponseEntity<ApiResponse<String>> deleteDriverById(@PathVariable("id") int driverId){
        this.driverService.deleteDriverById(driverId);
        return ResponseEntity.ok(ApiResponse.success("Driver Inactive"));
    }
}
