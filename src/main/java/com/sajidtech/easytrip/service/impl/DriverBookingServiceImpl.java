package com.sajidtech.easytrip.service.impl;

import com.sajidtech.easytrip.dto.response.BookingResponse;
import com.sajidtech.easytrip.emails.EmailService;
import com.sajidtech.easytrip.emails.EmailTemplate;
import com.sajidtech.easytrip.enums.Status;
import com.sajidtech.easytrip.enums.TripStatus;
import com.sajidtech.easytrip.exception.BookingNotFoundException;
import com.sajidtech.easytrip.exception.DriverNotFoundException;
import com.sajidtech.easytrip.model.Booking;
import com.sajidtech.easytrip.model.Customer;
import com.sajidtech.easytrip.model.Driver;
import com.sajidtech.easytrip.repository.CustomerRepository;
import com.sajidtech.easytrip.repository.DriverRepository;
import com.sajidtech.easytrip.service.DriverBookingService;
import com.sajidtech.easytrip.transformer.BookingTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DriverBookingServiceImpl implements DriverBookingService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    public List<BookingResponse> getAllBookings(String email) {
        Driver driver = checkValidDriver(email);
        return driver.getBooking().stream().map(booking -> getBookingResponseByBooking(booking, driver)).toList();
    }

    public List<BookingResponse> getAllCompletedBookings(String email) {
        Driver driver = checkValidDriver(email);
        return driver.getBooking().stream()
                .filter(booking-> booking.getTripStatus().equals(TripStatus.COMPLETED))
                .map(booking-> getBookingResponseByBooking(booking, driver)).collect(Collectors.toList());
    }

    public List<BookingResponse> getAllCancelledBookings(String email) {
        Driver driver = checkValidDriver(email);
        return driver.getBooking().stream()
                .filter(booking-> booking.getTripStatus().equals(TripStatus.CANCELLED))
                .map(booking -> getBookingResponseByBooking(booking, driver)).collect(Collectors.toList());
    }

    public BookingResponse getProgressBookings(String email) {
        Driver driver = checkValidDriver(email);
        Booking progressBooking = driver.getBooking().stream()
                .filter(booking-> booking.getTripStatus().equals(TripStatus.IN_PROGRESS)).findFirst()
                .orElseThrow(()-> new BookingNotFoundException("Driver has no one Booking who is IN_PROGRESS"));
        return getBookingResponseByBooking(progressBooking, driver);
    }

    public void completeBookingByDriver(String email) {
        Driver driver = checkValidDriver(email);
        Booking booking = driverGetActiveBooking(driver);
        Customer customer = customerRepository.findCustomerByBookingId(booking.getBookingId());

        booking.setTripStatus(TripStatus.COMPLETED);
        driver.getCab().setAvailable(true);
        driverRepository.save(driver);
        BookingResponse bookingResponse = BookingTransformer.bookingToBookingResponse(booking,driver.getCab(),driver,customer);
        //  EmailSender to the customer who ever booked the cab
//        EmailService.sendEmailToCustomer(EmailTemplate.getSubject(TripStatus.COMPLETED), bookingResponse, TripStatus.COMPLETED);
    }

    private Booking driverGetActiveBooking(Driver driver) {
        return driver.getBooking().stream().filter(b -> b.getTripStatus().equals(TripStatus.IN_PROGRESS))
                .findAny().orElseThrow(() -> new BookingNotFoundException("No one Booking is available in Driver List to the completion"));
    }

    private Driver checkValidDriver(String email) {
        Driver driver = driverRepository.findByEmail(email).orElseThrow(()-> new DriverNotFoundException("Driver Not Found"));
        if(driver.getStatus() == Status.INACTIVE){
            throw new RuntimeException("Driver is Inactive, Access denied ");
        }
        return driver;
    }

    private BookingResponse getBookingResponseByBooking(Booking booking, Driver driver) {
        Customer customer = customerRepository.findByBookingId(booking.getBookingId());
        return BookingTransformer.bookingToBookingResponseForDriver(booking, driver.getCab(),customer);
    }

}
