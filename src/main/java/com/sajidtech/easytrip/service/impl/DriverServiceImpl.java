package com.sajidtech.easytrip.service.impl;

import com.sajidtech.easytrip.enums.Status;
import com.sajidtech.easytrip.enums.TripStatus;
import com.sajidtech.easytrip.dto.request.DriverRequest;
import com.sajidtech.easytrip.dto.response.DriverResponse;
import com.sajidtech.easytrip.exception.DriverNotFoundException;
import com.sajidtech.easytrip.model.*;
import com.sajidtech.easytrip.repository.DriverRepository;
import com.sajidtech.easytrip.repository.UserRepository;
import com.sajidtech.easytrip.service.DriverService;
import com.sajidtech.easytrip.transformer.DriverTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DriverServiceImpl implements DriverService {

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public DriverResponse createProfile(DriverRequest driverRequest, String email) {
        User user = this.userRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));
        if(user.getProfileStatus().equals(Status.INACTIVE)){
            throw new RuntimeException("Driver is inactive. Access denied");
        }
        Driver driver = DriverTransformer.driverRequestToDriver(driverRequest);

        driver.setEmail(email);
        driver.setUser(user);
        Driver savedDriver = this.driverRepository.save(driver);
        return DriverTransformer.driverToDriverResponse(savedDriver);
    }

    public DriverResponse getProfile(String email) {
        Driver driver = checkValidDriver(email);
        return DriverTransformer.driverToDriverResponse(driver);
    }

    public void updateProfile(DriverRequest driverRequest, String email) {
        Driver driver = checkValidDriver(email);

        driver.setName(driverRequest.getName());
        driver.setAge(driverRequest.getAge());

        this.driverRepository.save(driver);
    }

    @Transactional
    public void profileInactive(String email) {
        Driver driver = checkValidDriver(email);
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
        driver.getUser().setProfileStatus(Status.INACTIVE);
        this.driverRepository.save(driver);
    }

    private Driver checkValidDriver(String email) {
        Driver driver = this.driverRepository.findByEmail(email)
                .orElseThrow(() -> new DriverNotFoundException("Driver not Found"));
        if(driver.getStatus() == Status.INACTIVE){
            throw new RuntimeException("Driver is inactive. Access denied");
        }
        return driver;
    }
}
