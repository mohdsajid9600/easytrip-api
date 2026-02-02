package com.sajidtech.easytrip.controller;

import com.sajidtech.easytrip.dto.response.*;
import com.sajidtech.easytrip.enums.Gender;
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
    public ResponseEntity<ApiResponse<PageResponse<CustomerResponse>>> getAllCustomer(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        PageResponse<CustomerResponse> responseList = this.adminService.getAllCustomer(page, size);
       return ResponseEntity.ok(ApiResponse.success("Customers fetched",responseList));
    }

    // Get customer by ID
    @GetMapping("/customer/search")
    public ResponseEntity<ApiResponse<CustomerResponse>> getCustomerById(@RequestParam("id") int customerId){
        CustomerResponse customerResponse = this.adminService.getCustomerById(customerId);
        return ResponseEntity.ok(ApiResponse.success("Customer found", customerResponse));
    }

    // Search by gender and age
    @GetMapping("/customers/search")
    public ResponseEntity<ApiResponse<PageResponse<CustomerResponse>>> getAllByGenderAndAge(
            @RequestParam("gender") Gender gender,
            @RequestParam("age") int age,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        PageResponse<CustomerResponse> responses = this.adminService.getAllByGenderAndAge(gender, age, page, size);
        return ResponseEntity.ok(ApiResponse.success("Customers fetched", responses));
    }

    // Get customers above age
    @GetMapping("/customers/search/greater")
    public ResponseEntity<ApiResponse<PageResponse<CustomerResponse>>> getAllGreaterThenAge(
            @RequestParam("age") int age,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        PageResponse<CustomerResponse> responses = this.adminService.getAllGreaterThenAge(age, page, size);
        return ResponseEntity.ok(ApiResponse.success("Filtered customers", responses));
    }
    // Get active customers
    @GetMapping("/customers/active")
    public ResponseEntity<ApiResponse<PageResponse<CustomerResponse>>> getActiveCustomers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        PageResponse<CustomerResponse> responseList = this.adminService.getActiveCustomers(page, size);
        return ResponseEntity.ok(ApiResponse.success("Active customers",responseList));
    }

    // Get inactive customers
    @GetMapping("/customers/inactive")
    public ResponseEntity<ApiResponse<PageResponse<CustomerResponse>>> getInactiveCustomers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        PageResponse<CustomerResponse> responseList = this.adminService.getInactiveCustomers(page, size);
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
    public ResponseEntity<ApiResponse<PageResponse<DriverResponse>>> getAllDrivers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        PageResponse<DriverResponse> responseList = this.adminService.getAllDrivers(page, size);
        return ResponseEntity.ok(ApiResponse.success("Registered Drivers", responseList));
    }

    @GetMapping("/driver/search")
    public ResponseEntity<ApiResponse<DriverResponse>> getDriverById(@RequestParam("id") int driverId){
        DriverResponse driverResponse = this.adminService.getDriverById(driverId);
        return ResponseEntity.ok(ApiResponse.success("Driver Found", driverResponse));
    }

    @GetMapping("/drivers/active")
    public ResponseEntity<ApiResponse<PageResponse<DriverResponse>>> getActiveDrivers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        PageResponse<DriverResponse> responseList = this.adminService.getActiveDrivers(page, size);
        return ResponseEntity.ok(ApiResponse.success("Active Drivers", responseList));
    }

    @GetMapping("/drivers/inactive")
    public ResponseEntity<ApiResponse<PageResponse<DriverResponse>>> getInactiveDrivers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        PageResponse<DriverResponse> responseList = this.adminService.getInactiveDrivers(page, size);
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
    public ResponseEntity<ApiResponse<PageResponse<CabResponse>>> getAllCabs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        PageResponse<CabResponse> responseList = this.adminService.getAllCabs(page, size);
        return ResponseEntity.ok(ApiResponse.success("Registered Cabs", responseList));
    }

    @GetMapping("/cab/search")
    public ResponseEntity<ApiResponse<CabResponse>> getCabById(@RequestParam("id") int cabId){
        CabResponse cabResponse = this.adminService.getCabById(cabId);
        return ResponseEntity.ok(ApiResponse.success("Cab Found", cabResponse));
    }

    @GetMapping("/cabs/active")
    public ResponseEntity<ApiResponse<PageResponse<CabResponse>>> getActiveCabs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        PageResponse<CabResponse> responseList = this.adminService.getActiveCabs(page, size);
        return ResponseEntity.ok(ApiResponse.success("Active Cabs",responseList));
    }

    @GetMapping("/cabs/inactive")
    public ResponseEntity<ApiResponse<PageResponse<CabResponse>>> getInactiveCabs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        PageResponse<CabResponse> responseList = this.adminService.getInactiveCabs(page, size);
        return ResponseEntity.ok(ApiResponse.success("Inactive Cabs", responseList));
    }

    @GetMapping("/cabs/available")
    public ResponseEntity<ApiResponse<PageResponse<CabResponse>>> getAvailableCabs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        PageResponse<CabResponse> responseList = this.adminService.getAvailableCabs(page, size);
        return ResponseEntity.ok(ApiResponse.success("Available Cabs", responseList));
    }

    @GetMapping("/cabs/unavailable")
    public ResponseEntity<ApiResponse<PageResponse<CabResponse>>> getUnavailableCabs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        PageResponse<CabResponse> responseList = this.adminService.getUnavailableCabs(page, size);
        return ResponseEntity.ok(ApiResponse.success("Unavailable Cabs", responseList));
    }


//_____________________________________________________ADMIN ACTION ON BOOKINGS_______________________________________________________________


    @GetMapping("/bookings")
    public ResponseEntity<ApiResponse<PageResponse<BookingResponse>>> getAllBookings(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        PageResponse<BookingResponse> responseList = this.adminService.getAllBookings(page, size);
        return ResponseEntity.ok(ApiResponse.success("Bookings fetched", responseList));
    }

    @GetMapping("/booking/search")
    public ResponseEntity<ApiResponse<BookingResponse>> getBookingById(@RequestParam("id") int bookingId){
        BookingResponse bookingResponse = this.adminService.getBookingById(bookingId);
        return ResponseEntity.ok(ApiResponse.success("Booking Found", bookingResponse));
    }

    @GetMapping("/bookings/customer")
    public ResponseEntity<ApiResponse<PageResponse<BookingResponse>>> getBookingsByCustomer(
            @RequestParam("id") int customerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        PageResponse<BookingResponse> responseList = this.adminService.getBookingsByCustomer(customerId, page, size);
        return ResponseEntity.ok(ApiResponse.success("Customers Bookings", responseList));
    }

    @GetMapping("/bookings/driver")
    public ResponseEntity<ApiResponse<PageResponse<BookingResponse>>> getBookingsByDriver(
            @RequestParam("id") int driverId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        PageResponse<BookingResponse> responseList = this.adminService.getBookingsByDriver(driverId, page, size);
        return ResponseEntity.ok(ApiResponse.success("Driver Bookings", responseList));
    }

    @GetMapping("/bookings/active")
    public ResponseEntity<ApiResponse<PageResponse<BookingResponse>>> getActiveBookings(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        PageResponse<BookingResponse> responseList = this.adminService.getActiveBookings(page, size);
        return ResponseEntity.ok(ApiResponse.success("Active Bookings", responseList));
    }

    @GetMapping("/bookings/complete")
    public ResponseEntity<ApiResponse<PageResponse<BookingResponse>>> getCompletedBookings(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        PageResponse<BookingResponse> responseList = this.adminService.getCompletedBookings(page, size);
        return ResponseEntity.ok(ApiResponse.success("Completed Bookings", responseList));
    }

    @GetMapping("/bookings/cancel")
    public ResponseEntity<ApiResponse<PageResponse<BookingResponse>>> getCancelledBookings(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        PageResponse<BookingResponse> responseList = this.adminService.getCancelledBookings(page, size);
        return ResponseEntity.ok(ApiResponse.success("Cancelled Bookings", responseList));
    }
}
