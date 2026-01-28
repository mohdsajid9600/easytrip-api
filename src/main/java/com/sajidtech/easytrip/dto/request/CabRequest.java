package com.sajidtech.easytrip.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CabRequest {

    @NotBlank(message = "Cab number is required")
    private String cabNumber;

    @NotBlank(message = "Cab model is required")
    private String cabModel;

    @NotNull(message = "Per km rate is required")
    @Positive(message = "Per km rate must be greater than zero")
    private double perKmRate;
}
