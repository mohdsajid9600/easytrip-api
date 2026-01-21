package com.sajidtech.easytrip.transformer;

import com.sajidtech.easytrip.dto.request.CabRequest;
import com.sajidtech.easytrip.dto.response.CabResponse;
import com.sajidtech.easytrip.model.Cab;
import com.sajidtech.easytrip.model.Driver;

public class CabTransformer {

    public static Cab cabRequestToCab(CabRequest cabRequest){
        return Cab.builder()
                .cabModel(cabRequest.getCabModel())
                .cabNumber(cabRequest.getCabNumber())
                .available(true)
                .perKmRate(cabRequest.getPerKmRate())
                .build();
    }

    public static CabResponse cabToCabResponse(Cab cab, Driver driver){
        return CabResponse.builder()
                .cabNumber(cab.getCabNumber())
                .cabModel(cab.getCabModel())
                .available(cab.isAvailable())
                .perKmRate(cab.getPerKmRate())
                .driverResponse(DriverTransformer.driverToDriverResponse(driver))
                .build();
    }

    public static CabResponse cabToCabResponseForAvailable(Cab cab){
        return CabResponse.builder()
                .cabNumber(cab.getCabNumber())
                .cabModel(cab.getCabModel())
                .available(cab.isAvailable())
                .perKmRate(cab.getPerKmRate())
                .build();
    }

    public static CabResponse cabToCabResponseForDriver(Cab cab){
        return CabResponse.builder()
                .cabNumber(cab.getCabNumber())
                .cabModel(cab.getCabModel())
                .perKmRate(cab.getPerKmRate())
                .build();
    }
    public static CabResponse cabToCabResponseForCustomer(Cab cab, Driver driver){
        return CabResponse.builder()
                .cabNumber(cab.getCabNumber())
                .cabModel(cab.getCabModel())
                .perKmRate(cab.getPerKmRate())
                .driverResponse(DriverTransformer.driverToDriverResponseForCustomer(driver))
                .build();
    }
}
