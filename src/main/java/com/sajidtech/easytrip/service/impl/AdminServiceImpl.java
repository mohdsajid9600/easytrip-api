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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

//___________________________________________BUSINESS LOGIC SECTION________________________________________________________________________


//_______________________________________________FOR CUSTOMER___________________________________________________________________________

    public PageResponse<CustomerResponse> getAllCustomer(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Customer> customerPage = this.customerRepository.findAll(pageable);
        List<CustomerResponse> customerResponseList = customerPage.getContent().stream()
                .map(CustomerTransformer::customerToCustomerResponse).toList();

        return PageTransformer.pageToPageResponse(customerPage, customerResponseList);
    }

    public CustomerResponse getCustomerById(Integer customerId) {
        Customer customer = getAndThrowCustomer(customerId);
        return CustomerTransformer.customerToCustomerResponse(customer);
    }

    public PageResponse<CustomerResponse> getAllByGenderAndAge(Gender gender, Byte age, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Customer> customersPage = this.customerRepository.findByGenderAndAge(gender, age, pageable);
        List<CustomerResponse> customerResponseList = customersPage.getContent().stream()
                .map(CustomerTransformer::customerToCustomerResponse).toList();

        return PageTransformer.pageToPageResponse(customersPage, customerResponseList);
    }

    public PageResponse<CustomerResponse> getAllGreaterThenAge(Byte age, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Customer> customersPage = this.customerRepository.getAllGreaterThenAge(age, pageable);
        List<CustomerResponse> customerResponseList = customersPage.getContent().stream()
                .map(CustomerTransformer::customerToCustomerResponse).toList();

        return PageTransformer.pageToPageResponse(customersPage, customerResponseList);
    }

    public PageResponse<CustomerResponse> getActiveCustomers(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Customer> customersPage  = this.customerRepository.findByStatus(Status.ACTIVE, pageable);
        List<CustomerResponse> customerResponseList = customersPage.getContent().stream()
                .map(CustomerTransformer::customerToCustomerResponse).toList();
        return PageTransformer.pageToPageResponse(customersPage, customerResponseList);
    }

    public PageResponse<CustomerResponse> getInactiveCustomers(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Customer> customersPage = this.customerRepository.findByStatus(Status.INACTIVE, pageable);
        List<CustomerResponse> customerResponseList = customersPage.getContent().stream()
                .map(CustomerTransformer::customerToCustomerResponse).toList();
        return PageTransformer.pageToPageResponse(customersPage, customerResponseList);
    }

    @Transactional
    public CustomerResponse activeCustomer(Integer customerId) {
        Customer customer = getAndThrowCustomer(customerId);
        customer.setStatus(Status.ACTIVE);
        customer.getUser().setProfileStatus(Status.ACTIVE);
        Customer savedCustomer = this.customerRepository.save(customer);
        return  CustomerTransformer.customerToCustomerResponse(savedCustomer);
    }

    @Transactional
    public CustomerResponse inActiveCustomer(Integer customerId) {
        Customer customer = getAndThrowCustomer(customerId);
        Booking booking =  getBookingOrNullByCustomer(customer);
        if(booking != null){
            booking.setTripStatus(TripStatus.CANCELLED);
            Driver driver = this.driverRepository.findDriverByBookingId(booking.getBookingId());
            driver.getCab().setIsAvailable(true);
            this.driverRepository.save(driver);
        }
        customer.setStatus(Status.INACTIVE);
        customer.getUser().setProfileStatus(Status.INACTIVE);
        Customer savedCustomer = this.customerRepository.save(customer);
        return  CustomerTransformer.customerToCustomerResponse(savedCustomer);
    }


//___________________________________________________FOR DRIVER________________________________________________________________________

    public PageResponse<DriverResponse> getAllDrivers(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Driver> driverPage = this.driverRepository.findAll(pageable);
        List<DriverResponse> driverResponseList = driverPage.getContent().stream()
                .map(DriverTransformer::driverToDriverResponse).toList();

        return PageTransformer.pageToPageResponse(driverPage, driverResponseList);
    }

    public DriverResponse getDriverById(Integer driverId) {
        Driver driver = getAndThrowDriver(driverId);
        return DriverTransformer.driverToDriverResponse(driver);
    }

    public PageResponse<DriverResponse> getActiveDrivers(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Driver> driverPage = this.driverRepository.findByStatus(Status.ACTIVE, pageable);
        List<DriverResponse> driverResponseList = driverPage.getContent().stream()
                .map(DriverTransformer::driverToDriverResponse).toList();
        return PageTransformer.pageToPageResponse(driverPage, driverResponseList);
    }

    public PageResponse<DriverResponse> getInactiveDrivers(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Driver> driverPage = this.driverRepository.findByStatus(Status.INACTIVE, pageable);
        List<DriverResponse> driverResponseList = driverPage.getContent().stream()
                .map(DriverTransformer::driverToDriverResponse).toList();
        return PageTransformer.pageToPageResponse(driverPage, driverResponseList);
    }

    @Transactional
    public DriverResponse activeDriver(Integer driverId) {
        Driver driver = getAndThrowDriver(driverId);
        driver.setStatus(Status.ACTIVE);
        driver.getUser().setProfileStatus(Status.ACTIVE);
        Cab cab = driver.getCab();
        if(cab != null){
            driver.getCab().setStatus(Status.ACTIVE);
            driver.getCab().setIsAvailable(true);
        }
        Driver savedDriver = this.driverRepository.save(driver);
        return DriverTransformer.driverToDriverResponse(savedDriver);
    }

    @Transactional
    public DriverResponse inActiveDriver(Integer driverId) {
        Driver driver = getAndThrowDriver(driverId);
        Booking booking = getActiveBookingByDriver(driver);
        if(booking != null){
            booking.setTripStatus(TripStatus.CANCELLED);
        }
        Cab cab = driver.getCab();
        if(cab != null){
            cab.setStatus(Status.INACTIVE);
            cab.setIsAvailable(false);
        }
        driver.setStatus(Status.INACTIVE);
        driver.getUser().setProfileStatus(Status.INACTIVE);
        Driver savedDriver = this.driverRepository.save(driver);
        return DriverTransformer.driverToDriverResponse(savedDriver);
    }


//_________________________________________________FOR CAB________________________________________________________________________

    public PageResponse<CabResponse> getAllCabs(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Cab> cabPage  = this.cabRepository.findAll(pageable);
        List<CabResponse> cabResponseList = cabPage.getContent().stream().map(this::getCabResponseByCab).toList();
        return PageTransformer.pageToPageResponse(cabPage, cabResponseList);
    }

    public CabResponse getCabById(Integer cabId) {
        Cab cab = getAndThrowCab(cabId);
        return getCabResponseByCab(cab);
    }

    public PageResponse<CabResponse> getActiveCabs(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Cab> cabPage = this.cabRepository.findByStatus(Status.ACTIVE, pageable);
        List<CabResponse> cabResponseList = cabPage.getContent().stream().map(this::getCabResponseByCab).toList();
        return PageTransformer.pageToPageResponse(cabPage, cabResponseList);
    }

    public PageResponse<CabResponse> getInactiveCabs(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Cab> cabPage = this.cabRepository.findByStatus(Status.INACTIVE, pageable);
        List<CabResponse> cabResponseList = cabPage.getContent().stream().map(this::getCabResponseByCab).toList();
        return PageTransformer.pageToPageResponse(cabPage, cabResponseList);
    }

    public PageResponse<CabResponse> getAvailableCabs(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Cab> cabPage = this.cabRepository.findAvailableCabs(pageable);
        List<CabResponse> cabResponseList = cabPage.getContent().stream().map(this::getCabResponseByCab).toList();
        return PageTransformer.pageToPageResponse(cabPage, cabResponseList);
    }

    public PageResponse<CabResponse> getUnavailableCabs(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Cab> cabPage = this.cabRepository.getUnavailableCab(pageable);
        List<CabResponse> cabResponseList = cabPage.getContent().stream().map(this::getCabResponseByCab).toList();
        return PageTransformer.pageToPageResponse(cabPage, cabResponseList);
    }


//__________________________________________________FOR BOOKING________________________________________________________________________

    public PageResponse<BookingResponse> getAllBookings(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Booking> bookingPage = this.bookingRepository.findAll(pageable);
        List<BookingResponse> bookingResponseList = bookingPage.getContent().stream()
                .map(this::getBookingResponseByBooking).toList();
        return PageTransformer.pageToPageResponse(bookingPage, bookingResponseList);
    }

    public BookingResponse getBookingById(Integer bookingId) {
        Booking booking = getAndThrowBooking(bookingId);
        return getBookingResponseByBooking(booking);
    }

    public PageResponse<BookingResponse> getBookingsByCustomer(Integer customerId,Integer page,Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Customer customer = getAndThrowCustomer(customerId);
        Page<Booking> bookingPage = this.bookingRepository.findBookingsByCustomer(customer, pageable);
        List<BookingResponse> bookingResponseList = bookingPage.getContent().stream()
                .map(this::getBookingResponseByBooking).toList();

        return PageTransformer.pageToPageResponse(bookingPage, bookingResponseList);
    }

    public PageResponse<BookingResponse> getBookingsByDriver(Integer driverId,Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Driver driver = getAndThrowDriver(driverId);
        Page<Booking> bookingPage = this.bookingRepository.findBookingsByDriver(driver, pageable);
        List<BookingResponse> bookingResponseList = bookingPage.getContent().stream()
                .map(this::getBookingResponseByBooking).toList();

        return PageTransformer.pageToPageResponse(bookingPage, bookingResponseList);
    }

    public PageResponse<BookingResponse> getActiveBookings(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Booking> bookingPage = this.bookingRepository.findByTripStatus(TripStatus.IN_PROGRESS, pageable);
        List<BookingResponse> bookingResponseList = bookingPage.getContent().stream()
                .map(this::getBookingResponseByBooking).toList();

        return PageTransformer.pageToPageResponse(bookingPage, bookingResponseList);
    }

    public PageResponse<BookingResponse> getCompletedBookings(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Booking> bookingPage = this.bookingRepository.findByTripStatus(TripStatus.COMPLETED, pageable);
        List<BookingResponse> bookingResponseList = bookingPage.getContent().stream()
                .map(this::getBookingResponseByBooking).toList();
        return PageTransformer.pageToPageResponse(bookingPage, bookingResponseList);
    }

    public PageResponse<BookingResponse> getCancelledBookings(Integer page, Integer size) {
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
    private Booking getActiveBookingByDriver(Driver driver) {
        return driver.getBooking().stream().filter(b -> b.getTripStatus().equals(TripStatus.IN_PROGRESS))
                .findFirst().orElse(null);
    }

    private Customer getAndThrowCustomer(Integer customerId) {
        return this.customerRepository.findById(customerId)
                .orElseThrow(()-> new CustomerNotFoundException("Customer Id is invalid"));
    }

    private Driver getAndThrowDriver(Integer driverId) {
        return this.driverRepository.findById(driverId).orElseThrow(()-> new DriverNotFoundException("Driver id is Invalid"));
    }

    private Cab getAndThrowCab(Integer cabId) {
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

    private Booking getAndThrowBooking(Integer bookingId) {
        return this.bookingRepository.findById(bookingId).orElseThrow(()-> new BookingNotFoundException("Booking id is Invalid"));
    }
}

