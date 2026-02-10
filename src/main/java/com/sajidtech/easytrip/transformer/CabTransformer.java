package com.sajidtech.easytrip.transformer;

import com.sajidtech.easytrip.enums.Status;
import com.sajidtech.easytrip.dto.request.CabRequest;
import com.sajidtech.easytrip.dto.response.CabResponse;
import com.sajidtech.easytrip.model.Cab;
import com.sajidtech.easytrip.model.Driver;

public class CabTransformer {

    public static Cab cabRequestToCab(CabRequest cabRequest){
        return Cab.builder()
                .cabModel(cabRequest.getCabModel())
                .cabNumber(cabRequest.getCabNumber())
                .isAvailable(true)
                .perKmRate(cabRequest.getPerKmRate())
                .status(Status.ACTIVE)
                .build();
    }

    public static CabResponse cabToCabResponse(Cab cab, Driver driver){
        return CabResponse.builder()
                .cabId(cab.getCabId())
                .cabNumber(cab.getCabNumber().toUpperCase())
                .cabModel(cab.getCabModel().toUpperCase())
                .isAvailable(cab.getIsAvailable())
                .perKmRate(cab.getPerKmRate())
                .status(cab.getStatus())
                .driverResponse(DriverTransformer.driverToDriverResponse(driver))
                .build();
    }

    public static CabResponse cabToCabResponseForAvailable(Cab cab){
        return CabResponse.builder()
                .cabNumber(cab.getCabNumber().toUpperCase())
                .cabModel(cab.getCabModel().toUpperCase())
                .isAvailable(cab.getIsAvailable())
                .perKmRate(cab.getPerKmRate())
                .build();
    }

    public static CabResponse cabToCabResponseForDriver(Cab cab){
        return CabResponse.builder()
                .cabNumber(cab.getCabNumber().toUpperCase())
                .cabModel(cab.getCabModel().toUpperCase())
                .perKmRate(cab.getPerKmRate())
                .isAvailable(cab.getIsAvailable())
                .status(cab.getStatus())
                .build();
    }
    public static CabResponse cabToCabResponseForCustomer(Cab cab, Driver driver){
        return CabResponse.builder()
                .cabNumber(cab.getCabNumber().toUpperCase())
                .cabModel(cab.getCabModel().toUpperCase())
                .perKmRate(cab.getPerKmRate())
                .driverResponse(DriverTransformer.driverToDriverResponseForCustomer(driver))
                .build();
    }
}
