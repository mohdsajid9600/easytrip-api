package com.sajidtech.easytrip.service;

import com.sajidtech.easytrip.dto.request.CabRequest;
import com.sajidtech.easytrip.dto.response.CabResponse;

import java.util.List;

public interface CabService {

    CabResponse createCab(CabRequest cabRequest, int driverId);

    CabResponse updateCabByDriver(CabRequest cabRequest, int driverId);

    List<CabResponse> getAllAvailableCabs();


}
