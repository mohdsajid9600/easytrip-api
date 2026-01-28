package com.sajidtech.easytrip.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class BookingRequest {

    @NotBlank(message = "Pickup location required")
    private String pickup;
    @NotBlank(message = "Destination required")
    private String destination;

    @NotNull(message = "Trip distance required")
    @Positive(message = "Trip distance should be positive")
    private double tripDistanceInKm;
}
