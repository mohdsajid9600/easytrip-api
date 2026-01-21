package com.sajidtech.easytrip.service;


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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


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
        Customer customer = customerRepository.findById(customerId).orElseThrow(()->
                new CustomerNotFoundException("Invalid customer Id for Booking : "+customerId));

        Booking oldBooking = customer.getBooking().stream().filter((b) -> b.getTripStatus().equals((TripStatus.IN_PROGRESS)))
                 .findAny().orElse(null);
        if(oldBooking != null){
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

        //EmailSender to the customer who ever booked the cab
//        sendEmail(bookingResponse);

        return bookingResponse;
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

    public BookingResponse updateBookedDetails(BookingRequest bookingRequest, int customerId) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(()-> new CustomerNotFoundException("Customer not found at booking updation"));

        Optional<Booking> OptionalBooking = bookingRepository.updateBookedDetails(customerId);
        if(OptionalBooking.isEmpty()) {
            throw new BookingNotFound("Customer with id "+ customerId+" have no one booking yet!");
        }
        Booking booking = OptionalBooking.get();

        Optional<Integer> OptionalDriverId = bookingRepository.getDriverIdByCustomerId(customerId);
        if(OptionalDriverId.isEmpty()){
            throw new DriverIdNotFoundException("We are not fetch DriverId from SQL");
        }
        int driverId = OptionalDriverId.get();

        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(()-> new DriverNotFoundException("Driver Not Found"));


        booking.setPickup(bookingRequest.getPickup());
        booking.setDestination(bookingRequest.getDestination());
        booking.setTripDistanceInKm(bookingRequest.getTripDistanceInKm());
        booking.setBillAmount(bookingRequest.getTripDistanceInKm() * driver.getCab().getPerKmRate());
        Booking savedBooking =  bookingRepository.save(booking);

        return BookingTransformer.bookingToBookingResponse(savedBooking, driver.getCab(), driver, customer);
    }

    public boolean cancelBooking(int customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(()-> new CustomerNotFoundException("Customer with Invalid Id"));

        Booking booking = customer.getBooking().stream().filter((b) -> b.getTripStatus().equals(TripStatus.IN_PROGRESS)).findAny()
                .orElseThrow(()-> new BookingNotFound("Customer has no one Booking which is IN_PROGRESS"));

        booking.setTripStatus(TripStatus.CANCELLED);
        bookingRepository.save(booking);

        Optional<Integer> OptionalDriverId  = bookingRepository.getDriverIdByBookingId(booking.getBookingId());
        OptionalDriverId.orElseThrow(()-> new NotFetchDriverId("Not Fetch driver id from database SQL Exception"));

        int driverId = OptionalDriverId.get();
        Driver driver = driverRepository.findById(driverId).orElseThrow(()-> new DriverNotFoundException("Driver Id is Invalid at the service layer"));

        driver.getCab().setAvailable(true);
        driverRepository.save(driver);

        return true;
    }

    public void completeBookingByDriver(int driverId) {
       Driver driver = driverRepository.findById(driverId).orElseThrow(()-> new DriverNotFoundException("Invalid Driver ID"));

      Booking booking = driver.getBooking().stream().filter(b -> b.getTripStatus().equals(TripStatus.IN_PROGRESS)).findAny().orElse(null);

      if(booking != null){
          booking.setTripStatus(TripStatus.COMPLETED);
          driver.getCab().setAvailable(true);
          driverRepository.save(driver);
      }else{
          throw new BookingNotFound("No one Booking is available in Driver List to the completion");
      }
    }
}
