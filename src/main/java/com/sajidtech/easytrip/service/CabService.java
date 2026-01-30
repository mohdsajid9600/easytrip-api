package com.sajidtech.easytrip.service;

import com.sajidtech.easytrip.dto.request.CabRequest;
import com.sajidtech.easytrip.dto.response.CabResponse;

import java.util.List;

public interface CabService {

    CabResponse createCab(CabRequest cabRequest, String email);

    CabResponse updateCabByDriver(CabRequest cabRequest, String email);

    List<CabResponse> getAllAvailableCabs();


}
