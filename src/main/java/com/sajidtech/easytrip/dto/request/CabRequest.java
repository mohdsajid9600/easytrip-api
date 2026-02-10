package com.sajidtech.easytrip.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CabRequest {

    @NotBlank(message = "Cab number is required")
    @Pattern(
            regexp = "^[A-Z]{2}[0-9]{2}[A-Z]{2}[0-9]{4}$",
            message = "Cab number must be in format like DL01AB1234"
    )
    private String cabNumber;

    @NotBlank(message = "Cab model is required")
    @Size(min = 2, max = 40, message = "Cab model must be between 2 and 40 characters")
    private String cabModel;

    @NotNull(message = "Per km rate is required")
    @Positive(message = "Per km rate must be greater than zero")
    private Double perKmRate;
}
