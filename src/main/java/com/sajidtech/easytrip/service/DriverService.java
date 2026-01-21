package com.sajidtech.easytrip.service;

import com.sajidtech.easytrip.Enum.TripStatus;
import com.sajidtech.easytrip.dto.request.DriverRequest;
import com.sajidtech.easytrip.dto.response.BookingResponse;
import com.sajidtech.easytrip.dto.response.DriverResponse;
import com.sajidtech.easytrip.exception.BookingNotFound;
import com.sajidtech.easytrip.exception.DriverNotFoundException;
import com.sajidtech.easytrip.model.Booking;
import com.sajidtech.easytrip.model.Cab;
import com.sajidtech.easytrip.model.Customer;
import com.sajidtech.easytrip.model.Driver;
import com.sajidtech.easytrip.repository.CustomerRepository;
import com.sajidtech.easytrip.repository.DriverRepository;
import com.sajidtech.easytrip.transformer.BookingTransformer;
import com.sajidtech.easytrip.transformer.DriverTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DriverService {

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    CustomerRepository customerRepository;

    public DriverResponse addDriverInfo(DriverRequest driverRequest) {
        Driver driver = DriverTransformer.driverRequestToDriver(driverRequest);
        Driver savedDriver = driverRepository.save(driver);
        return DriverTransformer.driverToDriverResponse(savedDriver);
    }

    public DriverResponse getDriverById(int id) {
        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> new DriverNotFoundException("Driver ID Invalid with : "+id));
        return DriverTransformer.driverToDriverResponse(driver);
    }

    public boolean updateDriverInfo(DriverRequest driverRequest, int driverId) {
        Optional<Driver> OptionalDriver = driverRepository.findById(driverId);
        if(OptionalDriver.isEmpty()) return false;

        Driver driver = OptionalDriver.get();

        driver.setName(driverRequest.getName());
        driver.setAge(driverRequest.getAge());
        driver.setEmail(driverRequest.getEmail());

        driverRepository.save(driver);

        return true;
    }

    public List<BookingResponse> getAllBookings(int driverId) {
        Driver driver = driverRepository.findById(driverId).orElseThrow(()-> new DriverNotFoundException("Driver id is Invalid"));

        return driver.getBooking().stream().map(booking->{

            Customer customer = customerRepository.findByBookingId(booking.getBookingId());
            return BookingTransformer.bookingToBookingResponseForDriver(booking, driver.getCab(),customer);

        }).collect(Collectors.toList());
    }

    public List<BookingResponse> getAllCompletedBookings(int driverId) {
        Driver driver = driverRepository.findById(driverId).orElseThrow(()-> new DriverNotFoundException("Driver id is Invalid"));

        return driver.getBooking().stream().filter(booking-> booking.getTripStatus().equals(TripStatus.COMPLETED)).map(booking->{

            Customer customer = customerRepository.findByBookingId(booking.getBookingId());
            return BookingTransformer.bookingToBookingResponseForDriver(booking, driver.getCab(),customer);

        }).collect(Collectors.toList());
    }

    public List<BookingResponse> getAllCancelledBookings(int driverId) {
        Driver driver = driverRepository.findById(driverId).orElseThrow(()-> new DriverNotFoundException("Driver id is Invalid"));

        return driver.getBooking().stream().filter(booking-> booking.getTripStatus().equals(TripStatus.CANCELLED)).map(booking->{

            Customer customer = customerRepository.findByBookingId(booking.getBookingId());
            return BookingTransformer.bookingToBookingResponseForDriver(booking, driver.getCab(),customer);

        }).collect(Collectors.toList());
    }


    public BookingResponse getAllInProgressBookings(int driverId) {
        Driver driver = driverRepository.findById(driverId).orElseThrow(()-> new DriverNotFoundException("Driver id is Invalid"));

        Booking progressBooking = driver.getBooking().stream().filter(booking-> booking.getTripStatus().equals(TripStatus.IN_PROGRESS)).findFirst()
                .orElseThrow(()-> new BookingNotFound("Driver has no one Booking who is IN_PROGRESS"));

            Customer customer = customerRepository.findByBookingId(progressBooking.getBookingId());
            return BookingTransformer.bookingToBookingResponseForDriver(progressBooking, driver.getCab(),customer);
    }
}
