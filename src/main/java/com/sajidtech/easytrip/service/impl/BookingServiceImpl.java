package com.sajidtech.easytrip.service.impl;


import com.sajidtech.easytrip.enums.Status;
import com.sajidtech.easytrip.enums.TripStatus;
import com.sajidtech.easytrip.dto.request.BookingRequest;
import com.sajidtech.easytrip.dto.response.BookingResponse;
import com.sajidtech.easytrip.emails.EmailTemplate;
import com.sajidtech.easytrip.exception.*;
import com.sajidtech.easytrip.model.Booking;
import com.sajidtech.easytrip.model.Cab;
import com.sajidtech.easytrip.model.Customer;
import com.sajidtech.easytrip.model.Driver;
import com.sajidtech.easytrip.repository.BookingRepository;
import com.sajidtech.easytrip.repository.CabRepository;
import com.sajidtech.easytrip.repository.CustomerRepository;
import com.sajidtech.easytrip.repository.DriverRepository;
import com.sajidtech.easytrip.service.BookingService;
import com.sajidtech.easytrip.transformer.BookingTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;



@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CabRepository cabRepository;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    JavaMailSender javaMailSender;

    public BookingResponse bookCab(BookingRequest bookingRequest, int customerId) {
        Customer customer = checkValidCustomer(customerId);

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
//        sendEmailToCustomer(EmailTemplate.getSubject(TripStatus.IN_PROGRESS), bookingResponse, TripStatus.IN_PROGRESS);

        return bookingResponse;
    }

    public BookingResponse updateBookedDetails(BookingRequest bookingRequest, int customerId) {
        Customer customer = checkValidCustomer(customerId);
        Booking booking = getProgressBookingByCustomer(customer);
        Driver driver = driverRepository.findDriverByBookingId(booking.getBookingId());

        booking.setPickup(bookingRequest.getPickup());
        booking.setDestination(bookingRequest.getDestination());
        booking.setTripDistanceInKm(bookingRequest.getTripDistanceInKm());
        booking.setBillAmount(bookingRequest.getTripDistanceInKm() * driver.getCab().getPerKmRate());
        Booking savedBooking =  bookingRepository.save(booking);

        return BookingTransformer.bookingToBookingResponse(savedBooking, driver.getCab(), driver, customer);
    }

    public void cancelBooking(int customerId) {
        Customer customer = checkValidCustomer(customerId);
        Booking booking = getProgressBookingByCustomer(customer);
        Driver driver = driverRepository.findDriverByBookingId(booking.getBookingId());

        booking.setTripStatus(TripStatus.CANCELLED);
        driver.getCab().setAvailable(true);
        driverRepository.save(driver);
        BookingResponse bookingResponse = BookingTransformer.bookingToBookingResponse(booking,driver.getCab(),driver,customer);
        //  EmailSender to the customer who ever cancel the cab
//        sendEmailToCustomer(EmailTemplate.getSubject(TripStatus.CANCELLED), bookingResponse, TripStatus.CANCELLED);
    }

    public void completeBookingByDriver(int driverId) {
       Driver driver = checkValidDriver(driverId);
       Booking booking = getProgressBookingByDriver(driver);
       Customer customer = customerRepository.findCustomerByBookingId(booking.getBookingId());

       booking.setTripStatus(TripStatus.COMPLETED);
       driver.getCab().setAvailable(true);
       driverRepository.save(driver);
       BookingResponse bookingResponse = BookingTransformer.bookingToBookingResponse(booking,driver.getCab(),driver,customer);
       //  EmailSender to the customer who ever booked the cab
//        sendEmailToCustomer(EmailTemplate.getSubject(TripStatus.COMPLETED), bookingResponse, TripStatus.COMPLETED);
    }

    private Booking getProgressBookingByDriver(Driver driver) {
        return driver.getBooking().stream().filter(b -> b.getTripStatus().equals(TripStatus.IN_PROGRESS))
                .findAny().orElseThrow(() -> new BookingNotFoundException("No one Booking is available in Driver List to the completion"));
    }

    private Booking getProgressBookingByCustomer(Customer customer) {
        return customer.getBooking().stream().filter((b) -> b.getTripStatus().equals(TripStatus.IN_PROGRESS)).findAny()
                .orElseThrow(()-> new BookingNotFoundException("Customer has no one Booking which is IN_PROGRESS"));
    }
    private Driver checkValidDriver(int driverId) {
       Driver driver = driverRepository.findById(driverId).orElseThrow(()-> new DriverNotFoundException("Invalid Driver ID"));
       if(driver.getStatus() == Status.INACTIVE){
           throw new RuntimeException("Driver is Inactive, Access denied ");
       }
       return driver;
    }

    private Customer checkValidCustomer(int customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(()-> new CustomerNotFoundException("Customer Id is Invalid with : "+customerId));
        if(customer.getStatus() == Status.INACTIVE){
            throw new RuntimeException("Customer is inactive. Access denied");
        }
        return customer;
    }

    private void sendEmailToCustomer(String subject, BookingResponse bookingResponse, TripStatus tripStatus){
        String formate = EmailTemplate.getEmailTemplate(
                tripStatus,
                bookingResponse
        );

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("easetriptrevler@gmail.com");
        simpleMailMessage.setTo(bookingResponse.getCustomerResponse().getEmail());
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(formate);
        javaMailSender.send(simpleMailMessage);
    }
}
