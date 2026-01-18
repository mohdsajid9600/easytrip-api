package com.sajidtech.easytrip.dto.request;

import lombok.Data;

@Data
public class BookingRequest {

    private String pickup;
    private String destination;
    private double tripDistanceInKm;
}
