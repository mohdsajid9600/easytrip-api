package com.sajidtech.easytrip.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.sajidtech.easytrip.Enum.TripStatus;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "pickup",
        "destination",
        "tripDistanceInKm",
        "tripStatus",
        "billAmount",
        "bookedAt",
        "lastUpdateAt",
        "customerResponse",
        "cabResponse"
})
public class BookingResponse {

    private String pickup;
    private String destination;
    private Double tripDistanceInKm;
    private TripStatus tripStatus;
    private Double billAmount;
    private Date bookedAt;
    private Date lastUpdateAt;


    private CustomerResponse customerResponse;
    private CabResponse cabResponse;
}
