package com.sajidtech.easytrip.service.impl;

import com.sajidtech.easytrip.dto.response.BookingResponse;
import com.sajidtech.easytrip.dto.response.PageResponse;
import com.sajidtech.easytrip.emails.EmailService;
import com.sajidtech.easytrip.emails.CustomerEmailFormate;
import com.sajidtech.easytrip.enums.Status;
import com.sajidtech.easytrip.enums.TripStatus;
import com.sajidtech.easytrip.exception.BookingNotFoundException;
import com.sajidtech.easytrip.exception.DriverNotFoundException;
import com.sajidtech.easytrip.model.Booking;
import com.sajidtech.easytrip.model.Customer;
import com.sajidtech.easytrip.model.Driver;
import com.sajidtech.easytrip.repository.BookingRepository;
import com.sajidtech.easytrip.repository.CustomerRepository;
import com.sajidtech.easytrip.repository.DriverRepository;
import com.sajidtech.easytrip.service.DriverBookingService;
import com.sajidtech.easytrip.transformer.BookingTransformer;
import com.sajidtech.easytrip.transformer.PageTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DriverBookingServiceImpl implements DriverBookingService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private EmailService emailService;

    public PageResponse<BookingResponse> getAllBookings(Integer page, Integer size, String email) {
        Pageable pageable = PageRequest.of(page, size);
        Driver driver = checkValidDriver(email);
        Page<Booking> bookingPage =
                this.bookingRepository.findBookingsByDriver(driver, pageable);

        List<BookingResponse> bookingResponseList = bookingPage.getContent().stream()
                .map(booking -> getBookingResponseByBooking(booking, driver)).toList();

        return PageTransformer.pageToPageResponse(bookingPage, bookingResponseList);
    }

    public PageResponse<BookingResponse> getAllCompletedBookings(Integer page, Integer size, String email) {
        Pageable pageable = PageRequest.of(page, size);
        Driver driver = checkValidDriver(email);
        Page<Booking> bookingPage =
                this.bookingRepository.findBookingsByDriver(driver, pageable);

        List<BookingResponse> bookingResponseList = bookingPage.getContent().stream()
                .filter(booking-> booking.getTripStatus().equals(TripStatus.COMPLETED))
                .map(booking-> getBookingResponseByBooking(booking, driver)).toList();

        return PageTransformer.pageToPageResponse(bookingPage, bookingResponseList);
    }

    public PageResponse<BookingResponse> getAllCancelledBookings(Integer page, Integer size, String email) {
        Pageable pageable = PageRequest.of(page, size);
        Driver driver = checkValidDriver(email);
        Page<Booking> bookingPage =
                this.bookingRepository.findBookingsByDriver(driver, pageable);

        List<BookingResponse> bookingResponseList = bookingPage.getContent().stream()
                .filter(booking-> booking.getTripStatus().equals(TripStatus.CANCELLED))
                .map(booking -> getBookingResponseByBooking(booking, driver)).toList();

        return PageTransformer.pageToPageResponse(bookingPage, bookingResponseList);
    }

    public BookingResponse getProgressBookings(String email) {
        Driver driver = checkValidDriver(email);
        Booking progressBooking = driver.getBooking().stream()
                .filter(booking-> booking.getTripStatus().equals(TripStatus.IN_PROGRESS)).findFirst()
                .orElseThrow(()-> new BookingNotFoundException("Driver has no one Active Ride"));
        return getBookingResponseByBooking(progressBooking, driver);
    }

    @Transactional
    public void completeBookingByDriver(String email) {
        Driver driver = checkValidDriver(email);
        Booking booking = driverGetActiveBooking(driver);
        Customer customer = this.customerRepository.findCustomerByBookingId(booking.getBookingId());

        booking.setTripStatus(TripStatus.COMPLETED);
        driver.getCab().setIsAvailable(true);
        this.driverRepository.save(driver);
        BookingResponse bookingResponse = BookingTransformer.bookingToBookingResponseForDriver(booking,driver.getCab(),customer);
        //  EmailSender to the customer who ever booked the cab
        emailService.sendEmailToCustomer(CustomerEmailFormate.getSubject(
                TripStatus.COMPLETED),
                bookingResponse,
                TripStatus.COMPLETED
        );
    }

    private Booking driverGetActiveBooking(Driver driver) {
        return driver.getBooking().stream().filter(b -> b.getTripStatus().equals(TripStatus.IN_PROGRESS))
                .findAny().orElseThrow(() -> new BookingNotFoundException("No one Booking is available in Driver List to the completion"));
    }

    private Driver checkValidDriver(String email) {
        Driver driver = this.driverRepository.findByEmail(email).orElseThrow(()-> new DriverNotFoundException("Driver Not Found"));
        if(driver.getStatus() == Status.INACTIVE){
            throw new RuntimeException("Driver is Inactive, Access denied ");
        }
        return driver;
    }

    private BookingResponse getBookingResponseByBooking(Booking booking, Driver driver) {
        Customer customer = this.customerRepository.findByBookingId(booking.getBookingId());
        return BookingTransformer.bookingToBookingResponseForDriver(booking, driver.getCab(),customer);
    }

}
