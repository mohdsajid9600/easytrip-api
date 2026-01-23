package com.sajidtech.easytrip.service;

import com.sajidtech.easytrip.Enum.Status;
import com.sajidtech.easytrip.dto.request.CabRequest;
import com.sajidtech.easytrip.dto.response.CabResponse;
import com.sajidtech.easytrip.exception.CabNotFoundException;
import com.sajidtech.easytrip.exception.DriverNotFoundException;
import com.sajidtech.easytrip.model.Cab;
import com.sajidtech.easytrip.model.Driver;
import com.sajidtech.easytrip.repository.CabRepository;
import com.sajidtech.easytrip.repository.DriverRepository;
import com.sajidtech.easytrip.transformer.CabTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CabService {

    @Autowired
    private CabRepository cabRepository;

    @Autowired
    private DriverRepository driverRepository;

    public CabResponse createCab(CabRequest cabRequest, int driverId) {
        Driver driver = checkExistenceOfDriver(driverId);
        if(driver.getCab() != null){
            throw new RuntimeException("Driver have already registered one cab !");
        }
        Cab cab = CabTransformer.cabRequestToCab(cabRequest);
        driver.setCab(cab);
        Driver savedDriver = driverRepository.save(driver);
        return CabTransformer.cabToCabResponse(savedDriver.getCab(), savedDriver);
    }

    public CabResponse updateCabByDriver(CabRequest cabRequest, int driverId) {
        Driver driver = checkExistenceOfDriver(driverId);
        Cab cab = driver.getCab();
        if(cab == null){
            throw new CabNotFoundException("Cab not found by Driver");
        }

        cab.setCabModel(cabRequest.getCabModel());
        cab.setCabNumber(cabRequest.getCabNumber());
        cab.setPerKmRate(cabRequest.getPerKmRate());
        Driver savedDriver = driverRepository.save(driver);

        return CabTransformer.cabToCabResponse(savedDriver.getCab(), savedDriver);
    }

    public List<CabResponse> getAllAvailableCabs() {
        List<Cab> availableCabs = cabRepository.getAllAvailableCab();
        return availableCabs.stream().map(CabTransformer::cabToCabResponseForAvailable).collect(Collectors.toList());
    }

    private Driver checkExistenceOfDriver(int driverId) {
        Driver driver = driverRepository.findById(driverId).orElseThrow(()-> new DriverNotFoundException("Driver id is Invalid"));
        if(driver.getStatus() == Status.INACTIVE){
            throw new RuntimeException("Driver is inactive. Access denied");
        }
        return driver;
    }
}
