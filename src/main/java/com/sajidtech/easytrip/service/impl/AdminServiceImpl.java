package com.sajidtech.easytrip.service.impl;

import com.sajidtech.easytrip.dto.response.*;
import com.sajidtech.easytrip.enums.Gender;
import com.sajidtech.easytrip.enums.Status;
import com.sajidtech.easytrip.enums.TripStatus;
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
import com.sajidtech.easytrip.transformer.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableArgumentResolver;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.stream.Collectors;

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

//___________________________________________BUSINESS LOGIC SECTION________________________________________________________________________


//_______________________________________________FOR CUSTOMER___________________________________________________________________________

    public PageResponse<CustomerResponse> getAllCustomer(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Customer> customerPage = this.customerRepository.findAll(pageable);
        List<CustomerResponse> customerResponseList = customerPage.getContent().stream()
                .map(CustomerTransformer::customerToCustomerResponse).toList();

        return PageTransformer.pageToPageResponse(customerPage, customerResponseList);
    }

    public CustomerResponse getCustomerById(int customerId) {
        Customer customer = getAndThrowCustomer(customerId);
        return CustomerTransformer.customerToCustomerResponse(customer);
    }

    public PageResponse<CustomerResponse> getAllByGenderAndAge(Gender gender, int age, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Customer> customersPage = this.customerRepository.findByGenderAndAge(gender, age, pageable);
        List<CustomerResponse> customerResponseList = customersPage.getContent().stream()
                .map(CustomerTransformer::customerToCustomerResponse).collect(Collectors.toList());

        return PageTransformer.pageToPageResponse(customersPage, customerResponseList);
    }

    public PageResponse<CustomerResponse> getAllGreaterThenAge(int age, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Customer> customersPage = this.customerRepository.getAllGreaterThenAge(age, pageable);
        List<CustomerResponse> customerResponseList = customersPage.getContent().stream()
                .map(CustomerTransformer::customerToCustomerResponse).collect(Collectors.toList());

        return PageTransformer.pageToPageResponse(customersPage, customerResponseList);
    }

    public PageResponse<CustomerResponse> getActiveCustomers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Customer> customersPage  = this.customerRepository.findByStatus(Status.ACTIVE, pageable);
        List<CustomerResponse> customerResponseList = customersPage.getContent().stream()
                .map(CustomerTransformer::customerToCustomerResponse).toList();
        return PageTransformer.pageToPageResponse(customersPage, customerResponseList);
    }

    public PageResponse<CustomerResponse> getInactiveCustomers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Customer> customersPage = this.customerRepository.findByStatus(Status.INACTIVE, pageable);
        List<CustomerResponse> customerResponseList = customersPage.getContent().stream()
                .map(CustomerTransformer::customerToCustomerResponse).toList();
        return PageTransformer.pageToPageResponse(customersPage, customerResponseList);
    }

    @Transactional
    public CustomerResponse activeCustomer(int customerId) {
        Customer customer = getAndThrowCustomer(customerId);
        customer.setStatus(Status.ACTIVE);
        customer.getUser().setProfileStatus(Status.ACTIVE);
        Customer savedCustomer = this.customerRepository.save(customer);
        return  CustomerTransformer.customerToCustomerResponse(savedCustomer);
    }

    @Transactional
    public CustomerResponse inActiveCustomer(int customerId) {
        Customer customer = getAndThrowCustomer(customerId);
        Booking booking =  getBookingOrNullByCustomer(customer);
        if(booking != null){
            booking.setTripStatus(TripStatus.CANCELLED);
            Driver driver = this.driverRepository.findDriverByBookingId(booking.getBookingId());
            driver.getCab().setAvailable(true);
            this.driverRepository.save(driver);
        }
        customer.setStatus(Status.INACTIVE);
        customer.getUser().setProfileStatus(Status.INACTIVE);
        Customer savedCustomer = this.customerRepository.save(customer);
        return  CustomerTransformer.customerToCustomerResponse(savedCustomer);
    }


//___________________________________________________FOR DRIVER________________________________________________________________________

    public PageResponse<DriverResponse> getAllDrivers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Driver> driverPage = this.driverRepository.findAll(pageable);
        List<DriverResponse> driverResponseList = driverPage.getContent().stream()
                .map(DriverTransformer::driverToDriverResponse).toList();

        return PageTransformer.pageToPageResponse(driverPage, driverResponseList);
    }

    public DriverResponse getDriverById(int driverId) {
        Driver driver = getAndThrowDriver(driverId);
        return DriverTransformer.driverToDriverResponse(driver);
    }

    public PageResponse<DriverResponse> getActiveDrivers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Driver> driverPage = this.driverRepository.findByStatus(Status.ACTIVE, pageable);
        List<DriverResponse> driverResponseList = driverPage.getContent().stream()
                .map(DriverTransformer::driverToDriverResponse).toList();
        return PageTransformer.pageToPageResponse(driverPage, driverResponseList);
    }

    public PageResponse<DriverResponse> getInactiveDrivers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Driver> driverPage = this.driverRepository.findByStatus(Status.INACTIVE, pageable);
        List<DriverResponse> driverResponseList = driverPage.getContent().stream()
                .map(DriverTransformer::driverToDriverResponse).toList();
        return PageTransformer.pageToPageResponse(driverPage, driverResponseList);
    }

    @Transactional
    public DriverResponse activeDriver(int driverId) {
        Driver driver = getAndThrowDriver(driverId);
        driver.setStatus(Status.ACTIVE);
        driver.getUser().setProfileStatus(Status.ACTIVE);
        Cab cab = driver.getCab();
        if(cab != null){
            driver.getCab().setStatus(Status.ACTIVE);
            driver.getCab().setAvailable(true);
        }
        Driver savedDriver = this.driverRepository.save(driver);
        return DriverTransformer.driverToDriverResponse(savedDriver);
    }

    @Transactional
    public DriverResponse inActiveDriver(int driverId) {
        Driver driver = getAndThrowDriver(driverId);
        Booking booking = getBookingOrNullByDriver(driver);
        if(booking != null){
            booking.setTripStatus(TripStatus.CANCELLED);
        }
        Cab cab = driver.getCab();
        if(cab != null){
            cab.setStatus(Status.INACTIVE);
            cab.setAvailable(false);
        }
        driver.setStatus(Status.INACTIVE);
        driver.getUser().setProfileStatus(Status.INACTIVE);
        Driver savedDriver = this.driverRepository.save(driver);
        return DriverTransformer.driverToDriverResponse(savedDriver);
    }


//_________________________________________________FOR CAB________________________________________________________________________

    public PageResponse<CabResponse> getAllCabs(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Cab> cabPage  = this.cabRepository.findAll(pageable);
        List<CabResponse> cabResponseList = cabPage.getContent().stream().map(this::getCabResponseByCab).toList();
        return PageTransformer.pageToPageResponse(cabPage, cabResponseList);
    }

    public CabResponse getCabById(int cabId) {
        Cab cab = getAndThrowCab(cabId);
        return getCabResponseByCab(cab);
    }

    public PageResponse<CabResponse> getActiveCabs(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Cab> cabPage = this.cabRepository.findByStatus(Status.ACTIVE, pageable);
        List<CabResponse> cabResponseList = cabPage.getContent().stream().map(this::getCabResponseByCab).toList();
        return PageTransformer.pageToPageResponse(cabPage, cabResponseList);
    }

    public PageResponse<CabResponse> getInactiveCabs(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Cab> cabPage = this.cabRepository.findByStatus(Status.INACTIVE, pageable);
        List<CabResponse> cabResponseList = cabPage.getContent().stream().map(this::getCabResponseByCab).toList();
        return PageTransformer.pageToPageResponse(cabPage, cabResponseList);
    }

    public PageResponse<CabResponse> getAvailableCabs(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Cab> cabPage = this.cabRepository.getAllAvailableCab(pageable);
        List<CabResponse> cabResponseList = cabPage.getContent().stream().map(this::getCabResponseByCab).toList();
        return PageTransformer.pageToPageResponse(cabPage, cabResponseList);
    }

    public PageResponse<CabResponse> getUnavailableCabs(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Cab> cabPage = this.cabRepository.getUnavailableCab(pageable);
        List<CabResponse> cabResponseList = cabPage.getContent().stream().map(this::getCabResponseByCab).toList();
        return PageTransformer.pageToPageResponse(cabPage, cabResponseList);
    }


//__________________________________________________FOR BOOKING________________________________________________________________________

    public PageResponse<BookingResponse> getAllBookings(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Booking> bookingPage = this.bookingRepository.findAll(pageable);
        List<BookingResponse> bookingResponseList = bookingPage.getContent().stream()
                .map(this::getBookingResponseByBooking).toList();
        return PageTransformer.pageToPageResponse(bookingPage, bookingResponseList);
    }

    public BookingResponse getBookingById(int bookingId) {
        Booking booking = getAndThrowBooking(bookingId);
        return getBookingResponseByBooking(booking);
    }

    public PageResponse<BookingResponse> getBookingsByCustomer(int customerId,int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Customer customer = getAndThrowCustomer(customerId);
        Page<Booking> bookingPage = bookingRepository.findBookingsByCustomer(customer, pageable);
        List<BookingResponse> bookingResponseList = bookingPage.getContent().stream()
                .map(this::getBookingResponseByBooking).toList();

        return PageTransformer.pageToPageResponse(bookingPage, bookingResponseList);
    }

    public PageResponse<BookingResponse> getBookingsByDriver(int driverId,int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Driver driver = getAndThrowDriver(driverId);
        Page<Booking> bookingPage = bookingRepository.findBookingsByDriver(driver, pageable);
        List<BookingResponse> bookingResponseList = bookingPage.getContent().stream()
                .map(this::getBookingResponseByBooking).toList();

        return PageTransformer.pageToPageResponse(bookingPage, bookingResponseList);
    }

    public PageResponse<BookingResponse> getActiveBookings(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Booking> bookingPage = this.bookingRepository.findByTripStatus(TripStatus.IN_PROGRESS, pageable);
        List<BookingResponse> bookingResponseList = bookingPage.getContent().stream()
                .map(this::getBookingResponseByBooking).toList();

        return PageTransformer.pageToPageResponse(bookingPage, bookingResponseList);
    }

    public PageResponse<BookingResponse> getCompletedBookings(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Booking> bookingPage = this.bookingRepository.findByTripStatus(TripStatus.COMPLETED, pageable);
        List<BookingResponse> bookingResponseList = bookingPage.getContent().stream()
                .map(this::getBookingResponseByBooking).toList();
        return PageTransformer.pageToPageResponse(bookingPage, bookingResponseList);
    }

    public PageResponse<BookingResponse> getCancelledBookings(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Booking> bookingPage = this.bookingRepository.findByTripStatus(TripStatus.CANCELLED, pageable);
        List<BookingResponse> bookingResponseList = bookingPage.getContent().stream()
                .map(this::getBookingResponseByBooking).toList();
        return PageTransformer.pageToPageResponse(bookingPage, bookingResponseList);
    }


//___________________________________________HELPER METHODS SECTION________________________________________________________________________

    private Booking getBookingOrNullByCustomer(Customer customer) {
        return customer.getBooking().stream().filter(b -> b.getTripStatus().equals(TripStatus.IN_PROGRESS))
                .findFirst().orElse(null);
    }
    private Booking getBookingOrNullByDriver(Driver driver) {
        return driver.getBooking().stream().filter(b -> b.getTripStatus().equals(TripStatus.IN_PROGRESS))
                .findFirst().orElse(null);
    }

    private Customer getAndThrowCustomer(int customerId) {
        return this.customerRepository.findById(customerId)
                .orElseThrow(()-> new CustomerNotFoundException("Customer Id is invalid"));
    }

    private Driver getAndThrowDriver(int driverId) {
        return this.driverRepository.findById(driverId).orElseThrow(()-> new DriverNotFoundException("Driver id is Invalid"));
    }

    private Cab getAndThrowCab(int cabId) {
        return this.cabRepository.findById(cabId).orElseThrow(()-> new CabNotFoundException("Cab id is Invalid"));
    }

    private CabResponse getCabResponseByCab(Cab cab) {
        Driver driver = this.cabRepository.getDriverByCabId(cab.getCabId());
        return CabTransformer.cabToCabResponse(driver.getCab(), driver);
    }

    private BookingResponse getBookingResponseByBooking(Booking b) {
        Customer customer = this.customerRepository.findCustomerByBookingId(b.getBookingId());
        Driver driver = this.driverRepository.findDriverByBookingId(b.getBookingId());
        return BookingTransformer.bookingToBookingResponse(b,driver.getCab(),driver, customer);
    }

    private Booking getAndThrowBooking(int bookingId) {
        return this.bookingRepository.findById(bookingId).orElseThrow(()-> new BookingNotFoundException("Booking id is Invalid"));
    }
}

