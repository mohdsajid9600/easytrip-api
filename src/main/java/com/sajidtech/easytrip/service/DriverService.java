package com.sajidtech.easytrip.service;

import com.sajidtech.easytrip.dto.request.DriverRequest;
import com.sajidtech.easytrip.dto.response.DriverResponse;


public interface DriverService {

    DriverResponse createProfile(DriverRequest driverRequest, String email);

    DriverResponse getProfile(String email);

    void updateProfile(DriverRequest driverRequest, String email);

    void profileInactive(String email);
}
