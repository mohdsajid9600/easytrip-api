package com.sajidtech.easytrip.controller;

import com.sajidtech.easytrip.dto.response.BookingResponse;
import com.sajidtech.easytrip.dto.response.CabResponse;
import com.sajidtech.easytrip.dto.response.CustomerResponse;
import com.sajidtech.easytrip.dto.response.DriverResponse;
import com.sajidtech.easytrip.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

//_____________________________________________________ADMIN ACTION ON CUSTOMERS_______________________________________________________________

    @GetMapping("/customers")
    public ResponseEntity<List<CustomerResponse>> getAllCustomer(){
       List<CustomerResponse> responseList = adminService.getAllCustomer();
       return ResponseEntity.ok(responseList);
    }

    @GetMapping("/customer/search")
    public ResponseEntity<CustomerResponse> getCustomerById(@RequestParam("id") int customerId){
        CustomerResponse customerResponse = adminService.getCustomerById(customerId);
        return ResponseEntity.status(HttpStatus.OK).body(customerResponse);
    }

    @GetMapping("/customers/active")
    public ResponseEntity<List<CustomerResponse>> getActiveCustomers(){
        List<CustomerResponse> responseList = adminService.getActiveCustomers();
        return ResponseEntity.status(HttpStatus.OK).body(responseList);
    }

    @GetMapping("/customers/inactive")
    public ResponseEntity<List<CustomerResponse>> getInactiveCustomers(){
        List<CustomerResponse> responseList = adminService.getInactiveCustomers();
        return ResponseEntity.status(HttpStatus.OK).body(responseList);
    }

    @PutMapping("/customer/{id}/active")
    public ResponseEntity<CustomerResponse> activeCustomer(@PathVariable("id") int customerId){
        CustomerResponse customerResponse = adminService.activeCustomer(customerId);
        return ResponseEntity.status(HttpStatus.OK).body(customerResponse);
    }

    @PutMapping("/customer/{id}/inactive")
    public ResponseEntity<CustomerResponse> inActiveCustomer(@PathVariable("id") int customerId){
        CustomerResponse customerResponse = adminService.inActiveCustomer(customerId);
        return ResponseEntity.status(HttpStatus.OK).body(customerResponse);
    }

//_____________________________________________________ADMIN ACTION ON DRIVERS________________________________________________________________


    @GetMapping("/drivers")
    public ResponseEntity<List<DriverResponse>> getAllDrivers(){
        List<DriverResponse> responseList = adminService.getAllDrivers();
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/driver/search")
    public ResponseEntity<DriverResponse> getDriverById(@RequestParam("id") int driverId){
        DriverResponse driverResponse = adminService.getDriverById(driverId);
        return ResponseEntity.status(HttpStatus.OK).body(driverResponse);
    }

    @GetMapping("/drivers/active")
    public ResponseEntity<List<DriverResponse>> getActiveDrivers(){
        List<DriverResponse> responseList = adminService.getActiveDrivers();
        return ResponseEntity.status(HttpStatus.OK).body(responseList);
    }

    @GetMapping("/drivers/inactive")
    public ResponseEntity<List<DriverResponse>> getInactiveDrivers(){
        List<DriverResponse> responseList = adminService.getInactiveDrivers();
        return ResponseEntity.status(HttpStatus.OK).body(responseList);
    }

    @PutMapping("/driver/{id}/active")
    public ResponseEntity<DriverResponse> activeDriver(@PathVariable("id") int driverId){
        DriverResponse driverResponse = adminService.activeDriver(driverId);
        return ResponseEntity.status(HttpStatus.OK).body(driverResponse);
    }

    @PutMapping("/driver/{id}/inactive")
    public ResponseEntity<DriverResponse> inActiveDriver(@PathVariable("id") int driverId){
        DriverResponse driverResponse = adminService.inActiveDriver(driverId);
        return ResponseEntity.status(HttpStatus.OK).body(driverResponse);
    }


////_____________________________________________________ADMIN ACTION ON CABS___________________________________________________________________


    @GetMapping("/cabs")
    public ResponseEntity<List<CabResponse>> getAllCabs(){
        List<CabResponse> responseList = adminService.getAllCabs();
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/cab/search")
    public ResponseEntity<CabResponse> getCabById(@RequestParam("id") int cabId){
        CabResponse cabResponse = adminService.getCabById(cabId);
        return ResponseEntity.status(HttpStatus.OK).body(cabResponse);
    }

    @GetMapping("/cabs/active")
    public ResponseEntity<List<CabResponse>> getActiveCabs(){
        List<CabResponse> responseList = adminService.getActiveCabs();
        return ResponseEntity.status(HttpStatus.OK).body(responseList);
    }

    @GetMapping("/cabs/inactive")
    public ResponseEntity<List<CabResponse>> getInactiveCabs(){
        List<CabResponse> responseList = adminService.getInactiveCabs();
        return ResponseEntity.status(HttpStatus.OK).body(responseList);
    }

    @GetMapping("/cabs/available")
    public ResponseEntity<List<CabResponse>> getAvailableCabs(){
        List<CabResponse> responseList = adminService.getAvailableCabs();
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/cabs/unavailable")
    public ResponseEntity<List<CabResponse>> getUnavailableCabs(){
        List<CabResponse> responseList = adminService.getUnavailableCabs();
        return ResponseEntity.ok(responseList);
    }


//_____________________________________________________ADMIN ACTION ON BOOKINGS_______________________________________________________________


    @GetMapping("/bookings")
    public ResponseEntity<List<BookingResponse>> getAllBookings(){
        List<BookingResponse> responseList = adminService.getAllBookings();
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/booking/search")
    public ResponseEntity<BookingResponse> getBookingById(@RequestParam("id") int bookingId){
        BookingResponse bookingResponse = adminService.getBookingById(bookingId);
        return ResponseEntity.status(HttpStatus.OK).body(bookingResponse);
    }

    @GetMapping("/bookings/customer")
    public ResponseEntity<List<BookingResponse>> getBookingsByCustomer(@RequestParam("id") int customerId){
        List<BookingResponse> responseList = adminService.getBookingsByCustomer(customerId);
        return ResponseEntity.status(HttpStatus.OK).body(responseList);
    }

    @GetMapping("/bookings/driver")
    public ResponseEntity<List<BookingResponse>> getBookingsByDriver(@RequestParam("id") int driverId){
        List<BookingResponse> responseList = adminService.getBookingsByDriver(driverId);
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/bookings/active")
    public ResponseEntity<List<BookingResponse>> getActiveBookings(){
        List<BookingResponse> responseList = adminService.getActiveBookings();
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/bookings/complete")
    public ResponseEntity<List<BookingResponse>> getCompletedBookings(){
        List<BookingResponse> responseList = adminService.getCompletedBookings();
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/bookings/cancel")
    public ResponseEntity<List<BookingResponse>> getCancelledBookings(){
        List<BookingResponse> responseList = adminService.getCancelledBookings();
        return ResponseEntity.ok(responseList);
    }
}
