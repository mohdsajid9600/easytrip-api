package com.sajidtech.easytrip.service.impl;

import com.sajidtech.easytrip.dto.response.PageResponse;
import com.sajidtech.easytrip.enums.Status;
import com.sajidtech.easytrip.dto.request.CabRequest;
import com.sajidtech.easytrip.dto.response.CabResponse;
import com.sajidtech.easytrip.exception.CabNotFoundException;
import com.sajidtech.easytrip.exception.DriverNotFoundException;
import com.sajidtech.easytrip.model.Cab;
import com.sajidtech.easytrip.model.Driver;
import com.sajidtech.easytrip.repository.CabRepository;
import com.sajidtech.easytrip.repository.DriverRepository;
import com.sajidtech.easytrip.service.CabService;
import com.sajidtech.easytrip.transformer.CabTransformer;
import com.sajidtech.easytrip.transformer.PageTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CabServiceImpl implements CabService {

    @Autowired
    private CabRepository cabRepository;

    @Autowired
    private DriverRepository driverRepository;


    @Override
    @Transactional
    public CabResponse createCab(CabRequest cabRequest, String email) {
        Driver driver = checkExistenceOfDriver(email);
        if(driver.getCab() != null){
            throw new RuntimeException("Driver have already registered one cab !");
        }
        Cab cab = CabTransformer.cabRequestToCab(cabRequest);
        driver.setCab(cab);
        Driver savedDriver = this.driverRepository.save(driver);
        return CabTransformer.cabToCabResponse(savedDriver.getCab(), savedDriver);
    }

    @Override
    @Transactional
    public CabResponse updateCabByDriver(CabRequest cabRequest, String email) {
        Driver driver = checkExistenceOfDriver(email);
        Cab cab = driver.getCab();
        if(cab == null){
            throw new CabNotFoundException("Cab not found by Driver");
        }
        if(!cab.getIsAvailable()){
            throw new RuntimeException("Cab Busy at the Moment");
        }
        cab.setCabModel(cabRequest.getCabModel());
        cab.setCabNumber(cabRequest.getCabNumber());
        cab.setPerKmRate(cabRequest.getPerKmRate());
        Driver savedDriver = this.driverRepository.save(driver);

        return CabTransformer.cabToCabResponse(savedDriver.getCab(), savedDriver);
    }

    @Override
    public PageResponse<CabResponse> getAllAvailableCabs(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Cab> availableCabs = this.cabRepository.getAllAvailableCab(pageable);
        List<CabResponse> cabResponses = availableCabs.stream().map(CabTransformer::cabToCabResponseForAvailable).toList();

        return PageTransformer.pageToPageResponse(availableCabs, cabResponses);
    }

    @Override
    public CabResponse getMyCab(String email) {
        Driver driver = checkExistenceOfDriver(email);
        Cab cab = driver.getCab();
        if(cab == null){
            throw new CabNotFoundException("Cab not found");
        }
        return CabTransformer.cabToCabResponseForDriver(cab);
    }

    private Driver checkExistenceOfDriver(String email) {
        Driver driver = this.driverRepository.findByEmail(email).orElseThrow(()-> new DriverNotFoundException("Driver Not Found"));
        if(driver.getStatus() == Status.INACTIVE){
            throw new RuntimeException("Driver is inactive. Access denied");
        }
        return driver;
    }
}
