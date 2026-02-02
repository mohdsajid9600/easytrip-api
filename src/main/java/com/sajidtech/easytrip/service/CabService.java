package com.sajidtech.easytrip.service;

import com.sajidtech.easytrip.dto.request.CabRequest;
import com.sajidtech.easytrip.dto.response.CabResponse;
import com.sajidtech.easytrip.dto.response.PageResponse;
import org.springframework.data.domain.Page;

public interface CabService {

    CabResponse createCab(CabRequest cabRequest, String email);

    CabResponse updateCabByDriver(CabRequest cabRequest, String email);

    PageResponse<CabResponse> getAllAvailableCabs(int page, int size);

    CabResponse getMuCab(String email);
}
