package com.sajidtech.easytrip.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "cabNumber",
        "cabModel",
        "perKmRate",
        "available",
        "driverResponse"
})
public class CabResponse {

    private String cabNumber;
    private String cabModel;
    private Double perKmRate;
    private Boolean available;
    private DriverResponse driverResponse;
}
