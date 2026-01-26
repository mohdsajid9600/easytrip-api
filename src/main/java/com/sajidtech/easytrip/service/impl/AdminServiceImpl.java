package com.sajidtech.easytrip.service.impl;

import com.sajidtech.easytrip.enums.Status;
import com.sajidtech.easytrip.enums.TripStatus;
import com.sajidtech.easytrip.dto.response.BookingResponse;
import com.sajidtech.easytrip.dto.response.CabResponse;
import com.sajidtech.easytrip.dto.response.CustomerResponse;
import com.sajidtech.easytrip.dto.response.DriverResponse;
import com.sajidtech.easytrip.exception.BookingNotFoundException;
import com.sajidtech.easytrip.exception.CabNotFoundException;
import com.sajidtech.easytrip.exception.CustomerNotFoundException;
import com.sajidtech.easytrip.exception.DriverNotFoundException;
import com.sajidtech.easytrip.model.Booking;
import com.sajidtech.easytrip.model.Cab;
import com.sajidtech.easytrip.model.Customer;
import com.sajidtech.easytrip.model.Driver;
import com.sajidtech.easytrip.repository.BookingRepository;
import com.sajidtech.easytrip.repository.CabRepository;
import com.sajidtech.easytrip.repository.CustomerRepository;
import com.sajidtech.easytrip.repository.DriverRepository;
import com.sajidtech.easytrip.service.AdminService;
import com.sajidtech.easytrip.transformer.BookingTransformer;
import com.sajidtech.easytrip.transformer.CabTransformer;
import com.sajidtech.easytrip.transformer.CustomerTransformer;
import com.sajidtech.easytrip.transformer.DriverTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private CabRepository cabRepository;

    @Autowired
    private BookingRepository bookingRepository;


    public List<CustomerResponse> getAllCustomer() {
        List<Customer> customerList = customerRepository.findAll();
        return customerList.stream().map(CustomerTransformer::customerToCustomerResponse).toList();
    }

    public CustomerResponse getCustomerById(int customerId) {
        Customer customer = getAndThrowCustomer(customerId);
        return CustomerTransformer.customerToCustomerResponse(customer);
    }

    public List<CustomerResponse> getActiveCustomers() {
       List<Customer> customerList = customerRepository.findByStatus(Status.ACTIVE);
      return customerList.stream().map(CustomerTransformer::customerToCustomerResponse).toList();
    }

    public List<CustomerResponse> getInactiveCustomers() {
        List<Customer> customerList = customerRepository.findByStatus(Status.INACTIVE);
        return customerList.stream().map(CustomerTransformer::customerToCustomerResponse).toList();
    }

    public CustomerResponse activeCustomer(int customerId) {
        Customer customer = getAndThrowCustomer(customerId);
        customer.setStatus(Status.ACTIVE);
        Customer savedCustomer = customerRepository.save(customer);
        return  CustomerTransformer.customerToCustomerResponse(savedCustomer);
    }

    public CustomerResponse inActiveCustomer(int customerId) {
        Customer customer = getAndThrowCustomer(customerId);
        Booking booking =  getBookingOrNullByCustomer(customer);
        if(booking != null){
            booking.setTripStatus(TripStatus.CANCELLED);
            Driver driver = driverRepository.findDriverByBookingId(booking.getBookingId());
            driver.getCab().setAvailable(true);
            driverRepository.save(driver);
        }
        customer.setStatus(Status.INACTIVE);
        Customer savedCustomer = customerRepository.save(customer);
        return  CustomerTransformer.customerToCustomerResponse(savedCustomer);
    }

    public List<DriverResponse> getAllDrivers() {
        List<Driver> driverList = driverRepository.findAll();
        return driverList.stream().map(DriverTransformer::driverToDriverResponse).toList();
    }

    public DriverResponse getDriverById(int driverId) {
        Driver driver = getAndThrowDriver(driverId);
        return DriverTransformer.driverToDriverResponse(driver);
    }

    public List<DriverResponse> getActiveDrivers() {
       List<Driver> driverList = driverRepository.findByStatus(Status.ACTIVE);
       return driverList.stream().map(DriverTransformer::driverToDriverResponse).toList();
    }

    public List<DriverResponse> getInactiveDrivers() {
        List<Driver> driverList = driverRepository.findByStatus(Status.INACTIVE);
        return driverList.stream().map(DriverTransformer::driverToDriverResponse).toList();
    }

    public DriverResponse activeDriver(int driverId) {
        Driver driver = getAndThrowDriver(driverId);
        driver.setStatus(Status.ACTIVE);
        Cab cab = driver.getCab();
        if(cab != null){
            driver.getCab().setStatus(Status.ACTIVE);
            driver.getCab().setAvailable(true);
        }
        Driver savedDriver = driverRepository.save(driver);
        return DriverTransformer.driverToDriverResponse(driver);
    }

    public DriverResponse inActiveDriver(int driverId) {
        Driver driver = getAndThrowDriver(driverId);
        Booking booking = getBookingOrNullByDriver(driver);
        if(booking != null){
            booking.setTripStatus(TripStatus.CANCELLED);
        }
        Cab cab = driver.getCab();
        if(cab != null){
            cab.setStatus(Status.INACTIVE);
        }
        driver.setStatus(Status.INACTIVE);
        Driver savedDriver = driverRepository.save(driver);
        return DriverTransformer.driverToDriverResponse(savedDriver);
    }

    public List<CabResponse> getAllCabs() {
        List<Cab> cabList = cabRepository.findAll();
        return cabList.stream().map(this::getCabResponseByCab).toList();
    }

    public CabResponse getCabById(int cabId) {
        Cab cab = getAndThrowCab(cabId);
        return getCabResponseByCab(cab);
    }

    public List<CabResponse> getActiveCabs() {
        List<Cab> cabList = cabRepository.findByStatus(Status.ACTIVE);
        return cabList.stream().map(this::getCabResponseByCab).toList();
    }

    public List<CabResponse> getInactiveCabs() {
        List<Cab> cabList = cabRepository.findByStatus(Status.INACTIVE);
        return cabList.stream().map(this::getCabResponseByCab).toList();
    }

    public List<CabResponse> getAvailableCabs() {
        List<Cab> cabList = cabRepository.getAllAvailableCab();
        return cabList.stream().map(this::getCabResponseByCab).toList();
    }

    public List<CabResponse> getUnavailableCabs() {
        List<Cab> cabList = cabRepository.getUnavailableCab();
        return cabList.stream().map(this::getCabResponseByCab).toList();
    }

    public List<BookingResponse> getAllBookings() {
        List<Booking> bookingList = bookingRepository.findAll();
        return bookingList.stream().map(this::getBookingResponseByBooking).toList();
    }

    public BookingResponse getBookingById(int bookingId) {
        Booking booking = getAndThrowBooking(bookingId);
        return getBookingResponseByBooking(booking);
    }

    public List<BookingResponse> getBookingsByCustomer(int customerId) {
        Customer customer = getAndThrowCustomer(customerId);
        return customer.getBooking().stream().map(this::getBookingResponseByBooking).toList();
    }

    public List<BookingResponse> getBookingsByDriver(int driverId) {
        Driver driver = getAndThrowDriver(driverId);
        return driver.getBooking().stream().map(this::getBookingResponseByBooking).toList();
    }

    public List<BookingResponse> getActiveBookings() {
        List<Booking> bookingList = bookingRepository.findByTripStatus(TripStatus.IN_PROGRESS);
        return bookingList.stream().map(this::getBookingResponseByBooking).toList();
    }

    public List<BookingResponse> getCompletedBookings() {
        List<Booking> bookingList = bookingRepository.findByTripStatus(TripStatus.COMPLETED);
        return bookingList.stream().map(this::getBookingResponseByBooking).toList();
    }

    public List<BookingResponse> getCancelledBookings() {
        List<Booking> bookingList = bookingRepository.findByTripStatus(TripStatus.CANCELLED);
        return bookingList.stream().map(this::getBookingResponseByBooking).toList();
    }

    private Booking getBookingOrNullByCustomer(Customer customer) {
        return customer.getBooking().stream().filter(b -> b.getTripStatus().equals(TripStatus.IN_PROGRESS))
                .findFirst().orElse(null);
    }
    private Booking getBookingOrNullByDriver(Driver driver) {
        return driver.getBooking().stream().filter(b -> b.getTripStatus().equals(TripStatus.IN_PROGRESS))
                .findFirst().orElse(null);
    }

    private Customer getAndThrowCustomer(int customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(()-> new CustomerNotFoundException("Customer Id is invalid"));
    }

    private Driver getAndThrowDriver(int driverId) {
        return driverRepository.findById(driverId).orElseThrow(()-> new DriverNotFoundException("Driver id is Invalid"));
    }

    private Cab getAndThrowCab(int cabId) {
        return cabRepository.findById(cabId).orElseThrow(()-> new CabNotFoundException("Cab id is Invalid"));
    }

    private CabResponse getCabResponseByCab(Cab cab) {
        Driver driver = cabRepository.getDriverByCabId(cab.getCabId());
        return CabTransformer.cabToCabResponse(driver.getCab(), driver);
    }

    private BookingResponse getBookingResponseByBooking(Booking b) {
        Customer customer = customerRepository.findCustomerByBookingId(b.getBookingId());
        Driver driver = driverRepository.findDriverByBookingId(b.getBookingId());
        return BookingTransformer.bookingToBookingResponse(b,driver.getCab(),driver, customer);
    }

    private Booking getAndThrowBooking(int bookingId) {
        return bookingRepository.findById(bookingId).orElseThrow(()-> new BookingNotFoundException("Booking id is Invalid"));
    }
}

