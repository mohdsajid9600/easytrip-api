package com.sajidtech.easytrip.transformer;

import com.sajidtech.easytrip.enums.Status;
import com.sajidtech.easytrip.dto.request.DriverRequest;
import com.sajidtech.easytrip.dto.response.DriverResponse;
import com.sajidtech.easytrip.model.Driver;

public class DriverTransformer {

    public static Driver driverRequestToDriver(DriverRequest driverRequest){

        return Driver.builder()
                .name(driverRequest.getName())
                .age(driverRequest.getAge())
                .mobileNo(driverRequest.getMobileNo())
                .license(driverRequest.getLicense())
                .experience(driverRequest.getExperience())
                .gender(driverRequest.getGender())
                .status(Status.ACTIVE)
                .build();
    }

    public static DriverResponse driverToDriverResponse(Driver driver){
        return DriverResponse.builder()
                .driverId(driver.getDriveId())
                .name(driver.getName().toUpperCase())
                .age(driver.getAge())
                .email(driver.getEmail())
                .mobileNo(driver.getMobileNo())
                .license(driver.getLicense())
                .experience(driver.getExperience())
                .gender(driver.getGender())
                .status(driver.getStatus())
                .createProfileAt(driver.getCreateProfileAt())
                .lastUpdateAt(driver.getLastUpdateAt())
                .build();
    }

    public static DriverResponse driverToDriverResponseForCustomer(Driver driver){
        return DriverResponse.builder()
                .name(driver.getName().toUpperCase())
                .age(driver.getAge())
                .email(driver.getEmail())
                .mobileNo(driver.getMobileNo())
                .gender(driver.getGender())
                .build();
    }
}
