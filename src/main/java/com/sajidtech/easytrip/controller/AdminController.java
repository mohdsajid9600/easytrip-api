package com.sajidtech.easytrip.controller;

import com.sajidtech.easytrip.dto.response.*;
import com.sajidtech.easytrip.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    // Admin service dependency
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

//_____________________________________________________ADMIN ACTION ON CUSTOMERS_______________________________________________________________

    // Get all customers
    @GetMapping("/customers")
    public ResponseEntity<ApiResponse<List<CustomerResponse>>> getAllCustomer(){
       List<CustomerResponse> responseList = this.adminService.getAllCustomer();
       return ResponseEntity.ok(ApiResponse.success("Customers fetched",responseList));
    }

    // Get customer by ID
    @GetMapping("/customer/search")
    public ResponseEntity<ApiResponse<CustomerResponse>> getCustomerById(@RequestParam("id") int customerId){
        CustomerResponse customerResponse = this.adminService.getCustomerById(customerId);
        return ResponseEntity.ok(ApiResponse.success("Customer found", customerResponse));
    }

    // Get active customers
    @GetMapping("/customers/active")
    public ResponseEntity<ApiResponse<List<CustomerResponse>>> getActiveCustomers(){
        List<CustomerResponse> responseList = this.adminService.getActiveCustomers();
        return ResponseEntity.ok(ApiResponse.success("Active customers",responseList));
    }

    // Get inactive customers
    @GetMapping("/customers/inactive")
    public ResponseEntity<ApiResponse<List<CustomerResponse>>> getInactiveCustomers(){
        List<CustomerResponse> responseList = this.adminService.getInactiveCustomers();
        return ResponseEntity.ok(ApiResponse.success("Inactive customers", responseList));
    }

    @PutMapping("/customer/{id}/active")
    public ResponseEntity<ApiResponse<CustomerResponse>> activeCustomer(@PathVariable("id") int customerId){
        CustomerResponse customerResponse = this.adminService.activeCustomer(customerId);
        return ResponseEntity.ok(ApiResponse.success("Active Customer Now", customerResponse));
    }

    @PutMapping("/customer/{id}/inactive")
    public ResponseEntity<ApiResponse<CustomerResponse>> inActiveCustomer(@PathVariable("id") int customerId){
        CustomerResponse customerResponse = this.adminService.inActiveCustomer(customerId);
        return ResponseEntity.ok(ApiResponse.success("Customer Inactive Now", customerResponse));
    }

//_____________________________________________________ADMIN ACTION ON DRIVERS________________________________________________________________


    @GetMapping("/drivers")
    public ResponseEntity<ApiResponse<List<DriverResponse>>> getAllDrivers(){
        List<DriverResponse> responseList = this.adminService.getAllDrivers();
        return ResponseEntity.ok(ApiResponse.success("Registered Drivers", responseList));
    }

    @GetMapping("/driver/search")
    public ResponseEntity<ApiResponse<DriverResponse>> getDriverById(@RequestParam("id") int driverId){
        DriverResponse driverResponse = this.adminService.getDriverById(driverId);
        return ResponseEntity.ok(ApiResponse.success("Driver Found", driverResponse));
    }

    @GetMapping("/drivers/active")
    public ResponseEntity<ApiResponse<List<DriverResponse>>> getActiveDrivers(){
        List<DriverResponse> responseList = this.adminService.getActiveDrivers();
        return ResponseEntity.ok(ApiResponse.success("Active Drivers", responseList));
    }

    @GetMapping("/drivers/inactive")
    public ResponseEntity<ApiResponse<List<DriverResponse>>> getInactiveDrivers(){
        List<DriverResponse> responseList = this.adminService.getInactiveDrivers();
        return ResponseEntity.ok(ApiResponse.success("Inactive Drivers", responseList));
    }

    @PutMapping("/driver/{id}/active")
    public ResponseEntity<ApiResponse<DriverResponse>> activeDriver(@PathVariable("id") int driverId){
        DriverResponse driverResponse = this.adminService.activeDriver(driverId);
        return ResponseEntity.ok(ApiResponse.success("Driver Activated", driverResponse));
    }

    @PutMapping("/driver/{id}/inactive")
    public ResponseEntity<ApiResponse<DriverResponse>> inActiveDriver(@PathVariable("id") int driverId){
        DriverResponse driverResponse = this.adminService.inActiveDriver(driverId);
        return ResponseEntity.ok(ApiResponse.success("Driver Inactivated", driverResponse));
    }


////_____________________________________________________ADMIN ACTION ON CABS___________________________________________________________________


    @GetMapping("/cabs")
    public ResponseEntity<ApiResponse<List<CabResponse>>> getAllCabs(){
        List<CabResponse> responseList = this.adminService.getAllCabs();
        return ResponseEntity.ok(ApiResponse.success("Registered Cabs", responseList));
    }

    @GetMapping("/cab/search")
    public ResponseEntity<ApiResponse<CabResponse>> getCabById(@RequestParam("id") int cabId){
        CabResponse cabResponse = this.adminService.getCabById(cabId);
        return ResponseEntity.ok(ApiResponse.success("Cab Found", cabResponse));
    }

    @GetMapping("/cabs/active")
    public ResponseEntity<ApiResponse<List<CabResponse>>> getActiveCabs(){
        List<CabResponse> responseList = this.adminService.getActiveCabs();
        return ResponseEntity.ok(ApiResponse.success("Active Cabs",responseList));
    }

    @GetMapping("/cabs/inactive")
    public ResponseEntity<ApiResponse<List<CabResponse>>> getInactiveCabs(){
        List<CabResponse> responseList = this.adminService.getInactiveCabs();
        return ResponseEntity.ok(ApiResponse.success("Inactive Cabs", responseList));
    }

    @GetMapping("/cabs/available")
    public ResponseEntity<ApiResponse<List<CabResponse>>> getAvailableCabs(){
        List<CabResponse> responseList = this.adminService.getAvailableCabs();
        return ResponseEntity.ok(ApiResponse.success("Available Cabs", responseList));
    }

    @GetMapping("/cabs/unavailable")
    public ResponseEntity<ApiResponse<List<CabResponse>>> getUnavailableCabs(){
        List<CabResponse> responseList = this.adminService.getUnavailableCabs();
        return ResponseEntity.ok(ApiResponse.success("Unavailable Cabs", responseList));
    }


//_____________________________________________________ADMIN ACTION ON BOOKINGS_______________________________________________________________


    @GetMapping("/bookings")
    public ResponseEntity<ApiResponse<List<BookingResponse>>> getAllBookings(){
        List<BookingResponse> responseList = this.adminService.getAllBookings();
        return ResponseEntity.ok(ApiResponse.success("Bookings fetched", responseList));
    }

    @GetMapping("/booking/search")
    public ResponseEntity<ApiResponse<BookingResponse>> getBookingById(@RequestParam("id") int bookingId){
        BookingResponse bookingResponse = this.adminService.getBookingById(bookingId);
        return ResponseEntity.ok(ApiResponse.success("Booking Found", bookingResponse));
    }

    @GetMapping("/bookings/customer")
    public ResponseEntity<ApiResponse<List<BookingResponse>>> getBookingsByCustomer(@RequestParam("id") int customerId){
        List<BookingResponse> responseList = this.adminService.getBookingsByCustomer(customerId);
        return ResponseEntity.ok(ApiResponse.success("Customers Bookings", responseList));
    }

    @GetMapping("/bookings/driver")
    public ResponseEntity<ApiResponse<List<BookingResponse>>> getBookingsByDriver(@RequestParam("id") int driverId){
        List<BookingResponse> responseList = this.adminService.getBookingsByDriver(driverId);
        return ResponseEntity.ok(ApiResponse.success("Driver Bookings", responseList));
    }

    @GetMapping("/bookings/active")
    public ResponseEntity<ApiResponse<List<BookingResponse>>> getActiveBookings(){
        List<BookingResponse> responseList = this.adminService.getActiveBookings();
        return ResponseEntity.ok(ApiResponse.success("Active Bookings", responseList));
    }

    @GetMapping("/bookings/complete")
    public ResponseEntity<ApiResponse<List<BookingResponse>>> getCompletedBookings(){
        List<BookingResponse> responseList = this.adminService.getCompletedBookings();
        return ResponseEntity.ok(ApiResponse.success("Completed Bookings", responseList));
    }

    @GetMapping("/bookings/cancel")
    public ResponseEntity<ApiResponse<List<BookingResponse>>> getCancelledBookings(){
        List<BookingResponse> responseList = this.adminService.getCancelledBookings();
        return ResponseEntity.ok(ApiResponse.success("Cancelled Bookings", responseList));
    }
}
