package com.sajidtech.easytrip.service;


import com.sajidtech.easytrip.Enum.Status;
import com.sajidtech.easytrip.Enum.TripStatus;
import com.sajidtech.easytrip.dto.request.BookingRequest;
import com.sajidtech.easytrip.dto.response.BookingResponse;
import com.sajidtech.easytrip.emailTemplate.EmailTemplate;
import com.sajidtech.easytrip.exception.*;
import com.sajidtech.easytrip.model.Booking;
import com.sajidtech.easytrip.model.Cab;
import com.sajidtech.easytrip.model.Customer;
import com.sajidtech.easytrip.model.Driver;
import com.sajidtech.easytrip.repository.BookingRepository;
import com.sajidtech.easytrip.repository.CabRepository;
import com.sajidtech.easytrip.repository.CustomerRepository;
import com.sajidtech.easytrip.repository.DriverRepository;
import com.sajidtech.easytrip.transformer.BookingTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;



@Service
public class BookingService {

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
                new CabUnavailabaleException(" Sorry !!  Cab Unavailable at this time"));

        Driver driver  = driverRepository.availableCabDriver(availableCab.getCabId());
        Booking booking = BookingTransformer.bookingRequestToBooking(bookingRequest, availableCab.getPerKmRate());
        Booking savedBooking = bookingRepository.save(booking);

        availableCab.setAvailable(false);
        driver.getBooking().add(savedBooking);
        customer.getBooking().add(savedBooking);

        Customer savedCustomer = customerRepository.save(customer);
        Driver savedDriver = driverRepository.save(driver);

        BookingResponse bookingResponse = BookingTransformer.bookingToBookingResponse(savedBooking,availableCab,savedDriver,savedCustomer);

//    //    EmailSender to the customer who ever booked the cab
//        sendEmail(bookingResponse);

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
    }

    public void completeBookingByDriver(int driverId) {
       Driver driver = checkValidDriver(driverId);
       Booking booking = getProgressBookingByDriver(driver);

       booking.setTripStatus(TripStatus.COMPLETED);
       driver.getCab().setAvailable(true);
       driverRepository.save(driver);
    }

    private Booking getProgressBookingByDriver(Driver driver) {
        return driver.getBooking().stream().filter(b -> b.getTripStatus().equals(TripStatus.IN_PROGRESS))
                .findAny().orElseThrow(() -> new BookingNotFound("No one Booking is available in Driver List to the completion"));
    }

    private Booking getProgressBookingByCustomer(Customer customer) {
        return customer.getBooking().stream().filter((b) -> b.getTripStatus().equals(TripStatus.IN_PROGRESS)).findAny()
                .orElseThrow(()-> new BookingNotFound("Customer has no one Booking which is IN_PROGRESS"));
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

    private void sendEmail(BookingResponse booking){
        String formate = EmailTemplate.bookingConfirmationTemplate(booking);

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("easetriptrevler@gmail.com");
        simpleMailMessage.setTo(booking.getCustomerResponse().getEmail());
        simpleMailMessage.setSubject("EasyTrip Booking Confirmation.");
        simpleMailMessage.setText(formate);
        javaMailSender.send(simpleMailMessage);
    }
}
