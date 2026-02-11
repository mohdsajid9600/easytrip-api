package com.sajidtech.easytrip.service.impl;

import com.sajidtech.easytrip.dto.request.BookingRequest;
import com.sajidtech.easytrip.dto.response.BookingResponse;
import com.sajidtech.easytrip.dto.response.PageResponse;
import com.sajidtech.easytrip.emails.EmailService;
import com.sajidtech.easytrip.emails.CustomerEmailFormate;
import com.sajidtech.easytrip.enums.Status;
import com.sajidtech.easytrip.enums.TripStatus;
import com.sajidtech.easytrip.exception.BookingNotFoundException;
import com.sajidtech.easytrip.exception.CabUnavailableException;
import com.sajidtech.easytrip.exception.CustomerNotFoundException;
import com.sajidtech.easytrip.model.Booking;
import com.sajidtech.easytrip.model.Cab;
import com.sajidtech.easytrip.model.Customer;
import com.sajidtech.easytrip.model.Driver;
import com.sajidtech.easytrip.repository.BookingRepository;
import com.sajidtech.easytrip.repository.CabRepository;
import com.sajidtech.easytrip.repository.CustomerRepository;
import com.sajidtech.easytrip.repository.DriverRepository;
import com.sajidtech.easytrip.service.CustomerBookingService;
import com.sajidtech.easytrip.transformer.BookingTransformer;
import com.sajidtech.easytrip.transformer.CustomerTransformer;
import com.sajidtech.easytrip.transformer.PageTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class CustomerBookingServiceImpl implements CustomerBookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CabRepository cabRepository;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private EmailService emailService;

    public PageResponse<BookingResponse> getAllBookings(Integer page, Integer size, String email) {
        Pageable pageable = PageRequest.of(page, size);
        Customer customer = checkValidCustomer(email);

        Page<Booking> bookingPage =
                this.bookingRepository.findBookingsByCustomer(customer, pageable);

        List<BookingResponse> bookingResponseList = bookingPage.getContent().stream()
                .map(this::getBookingResponseByBooking).toList();

        return PageTransformer.pageToPageResponse(bookingPage, bookingResponseList);
    }

    public PageResponse<BookingResponse> getAllCompletedBookings(Integer page, Integer size, String email) {
        Pageable pageable = PageRequest.of(page, size);
        Customer customer = checkValidCustomer(email);

        Page<Booking> bookingPage =
                this.bookingRepository.findBookingsByCustomer(customer, pageable);

        List<BookingResponse> bookingResponseList =  bookingPage.getContent().stream()
                .filter(booking -> booking.getTripStatus().equals(TripStatus.COMPLETED))
                .map(this::getBookingResponseByBooking).toList();

        return PageTransformer.pageToPageResponse(bookingPage, bookingResponseList);
    }

    public PageResponse<BookingResponse> getAllCancelledBookings(Integer page, Integer size, String email) {
        Pageable pageable = PageRequest.of(page, size);
        Customer customer = checkValidCustomer(email);

        Page<Booking> bookingPage =
                this.bookingRepository.findBookingsByCustomer(customer, pageable);

        List<BookingResponse> bookingResponseList = bookingPage.getContent().stream()
                .filter(booking -> booking.getTripStatus().equals(TripStatus.CANCELLED))
                .map(this::getBookingResponseByBooking).toList();

        return PageTransformer.pageToPageResponse(bookingPage, bookingResponseList);
    }

    public BookingResponse getProgressBookings(String email) {
        Customer customer = checkValidCustomer(email);
        Booking progressBooking = customer.getBooking().stream()
                .filter(booking -> booking.getTripStatus().equals(TripStatus.IN_PROGRESS))
                .findFirst().orElseThrow(()-> new BookingNotFoundException("Customer has no one Booking who are IN_PROGRESS"));
        return getBookingResponseByBooking(progressBooking);
    }

    @Transactional
    public BookingResponse bookCab(BookingRequest bookingRequest, String email) {
        Customer customer = checkValidCustomer(email);

        boolean hasOldBooking = customer.getBooking().stream().anyMatch(b-> b.getTripStatus().equals((TripStatus.IN_PROGRESS)));
        if(hasOldBooking){
            throw new RuntimeException("Pls complete or cancel Active ride first");
        }

        if(getAvailableCab().isEmpty()) {
            throw new CabUnavailableException("Cab unavailable at this time");
        }
        Cab availableCab  = getAvailableCab().get();

        Driver driver  = this.driverRepository.availableCabDriver(availableCab.getCabId());
        Booking booking = BookingTransformer.bookingRequestToBooking(bookingRequest, availableCab.getPerKmRate());
        Booking savedBooking = this.bookingRepository.save(booking);

        availableCab.setIsAvailable(false);
        driver.getBooking().add(savedBooking);
        customer.getBooking().add(savedBooking);

        Customer savedCustomer = this.customerRepository.save(customer);
        Driver savedDriver = this.driverRepository.save(driver);

        BookingResponse bookingResponse = BookingTransformer.bookingToBookingResponse(savedBooking,availableCab,savedDriver, savedCustomer);
        // EmailSender to the customer who ever booked the cab
//        emailService.sendEmailToCustomer(CustomerEmailFormate.getSubject(TripStatus.IN_PROGRESS), bookingResponse, TripStatus.IN_PROGRESS);

        return bookingResponse;
    }

    @Transactional
    public BookingResponse updateBookedDetails(BookingRequest bookingRequest, String email) {
        Customer customer = checkValidCustomer(email);
        Booking booking = getProgressBookingByCustomer(customer);
        Driver driver = this.driverRepository.findDriverByBookingId(booking.getBookingId());

        booking.setPickup(bookingRequest.getPickup());
        booking.setDestination(bookingRequest.getDestination());
        booking.setTripDistanceInKm(bookingRequest.getTripDistanceInKm());
        Booking savedBooking =  this.bookingRepository.save(booking);

        return BookingTransformer.bookingToBookingResponseForCustomer(savedBooking, driver.getCab(), driver);
    }

    @Transactional
    public void cancelBooking(String email) {
        Customer customer = checkValidCustomer(email);
        Booking booking = getProgressBookingByCustomer(customer);
        Driver driver = this.driverRepository.findDriverByBookingId(booking.getBookingId());

        booking.setTripStatus(TripStatus.CANCELLED);
        driver.getCab().setIsAvailable(true);
        this.driverRepository.save(driver);
        BookingResponse bookingResponse = BookingTransformer.bookingToBookingResponse(booking,driver.getCab(),driver, customer);
        //  EmailSender to the customer who ever cancel the cab
//        emailService.sendEmailToCustomer(CustomerEmailFormate.getSubject(TripStatus.CANCELLED), bookingResponse, TripStatus.CANCELLED);
    }

    private Booking getProgressBookingByCustomer(Customer customer) {
        return customer.getBooking().stream().filter((b) -> b.getTripStatus().equals(TripStatus.IN_PROGRESS)).findAny()
                .orElseThrow(()-> new BookingNotFoundException("Customer has no one Active Ride"));
    }

    private Customer checkValidCustomer(String email) {
        Customer customer = this.customerRepository.findByEmail(email)
                .orElseThrow(()-> new CustomerNotFoundException("Customer not found"));
        if(customer.getStatus() == Status.INACTIVE){
            throw new RuntimeException("Customer is inactive. Access denied");
        }
        return customer;
    }

    private BookingResponse getBookingResponseByBooking(Booking booking) {
        Driver driver = this.driverRepository.findDriverByBookingId(booking.getBookingId());
        return BookingTransformer.bookingToBookingResponseForCustomer(booking, driver.getCab(), driver);
    }

    private Optional<Cab> getAvailableCab() {
        long count = this.cabRepository.count();

        if (count == 0) return Optional.empty();

        int index = new Random().nextInt((int) count);

        Page<Cab> page = this.cabRepository.findAvailableCabs(PageRequest.of(index, 1));
        return page.getContent().stream().findFirst();
    }

}
