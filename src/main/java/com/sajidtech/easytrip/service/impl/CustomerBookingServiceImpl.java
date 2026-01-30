package com.sajidtech.easytrip.service.impl;

import com.sajidtech.easytrip.dto.request.BookingRequest;
import com.sajidtech.easytrip.dto.response.BookingResponse;
import com.sajidtech.easytrip.emails.EmailService;
import com.sajidtech.easytrip.emails.EmailTemplate;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<BookingResponse> getAllBookings(String email) {
        Customer customer = checkValidCustomer(email);
        return customer.getBooking().stream().map(this::getBookingResponseByBooking).toList();
    }

    public List<BookingResponse> getAllCompletedBookings(String email) {
        Customer customer = checkValidCustomer(email);
        return customer.getBooking().stream()
                .filter(booking -> booking.getTripStatus().equals(TripStatus.COMPLETED))
                .map(this::getBookingResponseByBooking).toList();
    }

    public List<BookingResponse> getAllCancelledBookings(String email) {
        Customer customer = checkValidCustomer(email);
        return customer.getBooking().stream()
                .filter(booking -> booking.getTripStatus().equals(TripStatus.CANCELLED))
                .map(this::getBookingResponseByBooking).collect(Collectors.toList());
    }

    public BookingResponse getProgressBookings(String email) {
        Customer customer = checkValidCustomer(email);
        Booking progressBooking = customer.getBooking().stream()
                .filter(booking -> booking.getTripStatus().equals(TripStatus.IN_PROGRESS))
                .findFirst().orElseThrow(()-> new BookingNotFoundException("Customer has no one Booking who are IN_PROGRESS"));
        return getBookingResponseByBooking(progressBooking);
    }

    public BookingResponse bookCab(BookingRequest bookingRequest, String email) {
        Customer customer = checkValidCustomer(email);

        boolean hasOldBooking = customer.getBooking().stream().anyMatch(b-> b.getTripStatus().equals((TripStatus.IN_PROGRESS)));
        if(hasOldBooking){
            throw new RuntimeException("The customer has already been One Journey which status is IN_PROGRESS");
        }

        Cab availableCab  = cabRepository.getAvailableCab().orElseThrow(()->
                new CabUnavailableException("Cab unavailable at this time"));

        Driver driver  = driverRepository.availableCabDriver(availableCab.getCabId());
        Booking booking = BookingTransformer.bookingRequestToBooking(bookingRequest, availableCab.getPerKmRate());
        Booking savedBooking = bookingRepository.save(booking);

        availableCab.setAvailable(false);
        driver.getBooking().add(savedBooking);
        customer.getBooking().add(savedBooking);

        Customer savedCustomer = customerRepository.save(customer);
        Driver savedDriver = driverRepository.save(driver);

        BookingResponse bookingResponse = BookingTransformer.bookingToBookingResponse(savedBooking,availableCab,savedDriver,savedCustomer);

        // EmailSender to the customer who ever booked the cab
//        EmailService.sendEmailToCustomer(EmailTemplate.getSubject(TripStatus.IN_PROGRESS), bookingResponse, TripStatus.IN_PROGRESS);

        return bookingResponse;
    }

    public BookingResponse updateBookedDetails(BookingRequest bookingRequest, String email) {
        Customer customer = checkValidCustomer(email);
        Booking booking = getProgressBookingByCustomer(customer);
        Driver driver = driverRepository.findDriverByBookingId(booking.getBookingId());

        booking.setPickup(bookingRequest.getPickup());
        booking.setDestination(bookingRequest.getDestination());
        booking.setTripDistanceInKm(bookingRequest.getTripDistanceInKm());
        booking.setBillAmount(bookingRequest.getTripDistanceInKm() * driver.getCab().getPerKmRate());
        Booking savedBooking =  bookingRepository.save(booking);

        return BookingTransformer.bookingToBookingResponse(savedBooking, driver.getCab(), driver, customer);
    }

    public void cancelBooking(String email) {
        Customer customer = checkValidCustomer(email);
        Booking booking = getProgressBookingByCustomer(customer);
        Driver driver = driverRepository.findDriverByBookingId(booking.getBookingId());

        booking.setTripStatus(TripStatus.CANCELLED);
        driver.getCab().setAvailable(true);
        driverRepository.save(driver);
        BookingResponse bookingResponse = BookingTransformer.bookingToBookingResponse(booking,driver.getCab(),driver,customer);
        //  EmailSender to the customer who ever cancel the cab
//        EmailService.sendEmailToCustomer(EmailTemplate.getSubject(TripStatus.CANCELLED), bookingResponse, TripStatus.CANCELLED);
    }

    private Booking getProgressBookingByCustomer(Customer customer) {
        return customer.getBooking().stream().filter((b) -> b.getTripStatus().equals(TripStatus.IN_PROGRESS)).findAny()
                .orElseThrow(()-> new BookingNotFoundException("Customer has no one Booking which is IN_PROGRESS"));
    }

    private Customer checkValidCustomer(String email) {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(()-> new CustomerNotFoundException("Customer not found"));
        if(customer.getStatus() == Status.INACTIVE){
            throw new RuntimeException("Customer is inactive. Access denied");
        }
        return customer;
    }

    private BookingResponse getBookingResponseByBooking(Booking booking) {
        Driver driver = driverRepository.findDriverByBookingId(booking.getBookingId());
        return BookingTransformer.bookingToBookingResponseForCustomer(booking, driver.getCab(), driver);
    }
}
