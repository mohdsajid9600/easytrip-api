package com.sajidtech.easytrip.service.impl;

import com.sajidtech.easytrip.enums.Status;
import com.sajidtech.easytrip.enums.TripStatus;
import com.sajidtech.easytrip.dto.request.DriverRequest;
import com.sajidtech.easytrip.dto.response.BookingResponse;
import com.sajidtech.easytrip.dto.response.DriverResponse;
import com.sajidtech.easytrip.exception.BookingNotFoundException;
import com.sajidtech.easytrip.exception.DriverNotFoundException;
import com.sajidtech.easytrip.model.Booking;
import com.sajidtech.easytrip.model.Cab;
import com.sajidtech.easytrip.model.Customer;
import com.sajidtech.easytrip.model.Driver;
import com.sajidtech.easytrip.repository.CustomerRepository;
import com.sajidtech.easytrip.repository.DriverRepository;
import com.sajidtech.easytrip.service.DriverService;
import com.sajidtech.easytrip.transformer.BookingTransformer;
import com.sajidtech.easytrip.transformer.DriverTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DriverServiceImpl implements DriverService {

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public DriverResponse addDriverInfo(DriverRequest driverRequest) {
        Driver driver = DriverTransformer.driverRequestToDriver(driverRequest);
        Driver savedDriver = driverRepository.save(driver);
        return DriverTransformer.driverToDriverResponse(savedDriver);
    }

    public DriverResponse getDriverById(int driverId) {
        Driver driver = checkValidDriver(driverId);
        return DriverTransformer.driverToDriverResponse(driver);
    }

    public void updateDriverInfo(DriverRequest driverRequest, int driverId) {
        Driver driver = checkValidDriver(driverId);

        driver.setName(driverRequest.getName());
        driver.setAge(driverRequest.getAge());
        driver.setEmail(driverRequest.getEmail());

        driverRepository.save(driver);
    }

    public List<BookingResponse> getAllBookings(int driverId) {
        Driver driver = checkValidDriver(driverId);
        return driver.getBooking().stream().map(booking -> getBookingResponseByBooking(booking, driver)).toList();
    }

    public List<BookingResponse> getAllCompletedBookings(int driverId) {
        Driver driver = checkValidDriver(driverId);
        return driver.getBooking().stream()
                .filter(booking-> booking.getTripStatus().equals(TripStatus.COMPLETED))
                .map(booking-> getBookingResponseByBooking(booking, driver)).collect(Collectors.toList());
    }

    public List<BookingResponse> getAllCancelledBookings(int driverId) {
        Driver driver = checkValidDriver(driverId);
        return driver.getBooking().stream()
                .filter(booking-> booking.getTripStatus().equals(TripStatus.CANCELLED))
                .map(booking -> getBookingResponseByBooking(booking, driver)).collect(Collectors.toList());
    }


    public BookingResponse getAllInProgressBookings(int driverId) {
        Driver driver = checkValidDriver(driverId);
        Booking progressBooking = driver.getBooking().stream()
                .filter(booking-> booking.getTripStatus().equals(TripStatus.IN_PROGRESS)).findFirst()
                .orElseThrow(()-> new BookingNotFoundException("Driver has no one Booking who is IN_PROGRESS"));
        return getBookingResponseByBooking(progressBooking, driver);
    }

    public void deleteDriverById(int driverId) {
        Driver driver = checkValidDriver(driverId);
        boolean hasActiveBooking = driver.getBooking().stream()
                .anyMatch(booking -> booking.getTripStatus().equals(TripStatus.IN_PROGRESS));
        if(hasActiveBooking){
            throw new RuntimeException("Driver cannot be deleted because it has one Booking IN_PROGRESS");
        }
        Cab cab = driver.getCab();
        if(cab != null){
            cab.setAvailable(false);
            cab.setStatus(Status.INACTIVE);
        }
        driver.setStatus(Status.INACTIVE);
        driverRepository.save(driver);
    }

    private BookingResponse getBookingResponseByBooking(Booking booking, Driver driver) {
        Customer customer = customerRepository.findByBookingId(booking.getBookingId());
        return BookingTransformer.bookingToBookingResponseForDriver(booking, driver.getCab(),customer);
    }

    private Driver checkValidDriver(int driverId) {
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new DriverNotFoundException("Driver ID Invalid with : "+driverId));
        if(driver.getStatus() == Status.INACTIVE){
            throw new RuntimeException("Driver is inactive. Access denied");
        }
        return driver;
    }
}
