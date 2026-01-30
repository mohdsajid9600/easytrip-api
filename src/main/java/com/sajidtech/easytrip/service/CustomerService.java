package com.sajidtech.easytrip.service;

import com.sajidtech.easytrip.dto.request.CustomerRequest;
import com.sajidtech.easytrip.dto.response.CustomerResponse;


public interface CustomerService {

    CustomerResponse createProfile(CustomerRequest customerRequest, String email );

    CustomerResponse getCustomerInfo(String email);

    void updateCustomerInfo(CustomerRequest customerRequest, String email);

    void deactivateProfile(String email);
}
