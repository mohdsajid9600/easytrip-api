package com.sajidtech.easytrip.controller;

import com.sajidtech.easytrip.Enum.TripStatus;
import com.sajidtech.easytrip.dto.request.DriverRequest;
import com.sajidtech.easytrip.dto.response.BookingResponse;
import com.sajidtech.easytrip.dto.response.DriverResponse;
import com.sajidtech.easytrip.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/drivers")
public class DriverController {

    @Autowired
    private DriverService driverService;

    @PostMapping("/register/driver")
    public ResponseEntity<DriverResponse> addDriverInfo(@RequestBody DriverRequest driverRequest){
        DriverResponse driverResponse = driverService.addDriverInfo(driverRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(driverResponse);
    }

    @GetMapping("/driver")
    public ResponseEntity<DriverResponse> getDriverById(@RequestParam("id") int id){
        DriverResponse driverResponse = driverService.getDriverById(id);
        return new ResponseEntity<>(driverResponse, HttpStatus.FOUND);
    }

    @PutMapping("/driver/{id}/update")
    public ResponseEntity<String> updateDriverInfo(@RequestBody DriverRequest driverRequest,
                                                   @PathVariable("id") int driverId){
        boolean isUpdated = driverService.updateDriverInfo(driverRequest, driverId);
        if(isUpdated){
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Your Record updated successfully !");
        }
        return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
    }

    @GetMapping("/driver/{id}/bookings")
    public ResponseEntity<List<BookingResponse>> getAllBookings(@PathVariable("id") int driverId){
        List<BookingResponse> responses = driverService.getAllBookings(driverId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/driver/{id}/bookings/completed")
    public ResponseEntity<List<BookingResponse>> getAllCompletedBookings(@PathVariable("id") int driverId){
        List<BookingResponse> responses = driverService.getAllCompletedBookings(driverId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/driver/{id}/bookings/cancelled")
    public ResponseEntity<List<BookingResponse>> getAllCancelledBookings(@PathVariable("id") int driverId){
        List<BookingResponse> responses = driverService.getAllCancelledBookings(driverId);
        return ResponseEntity.ok(responses);
    }
    @GetMapping("/driver/{id}/bookings/in-progress")
    public ResponseEntity<BookingResponse> getAllInProgressBookings(@PathVariable("id") int driverId){
        BookingResponse response = driverService.getAllInProgressBookings(driverId);
        return ResponseEntity.ok(response);
    }
}
