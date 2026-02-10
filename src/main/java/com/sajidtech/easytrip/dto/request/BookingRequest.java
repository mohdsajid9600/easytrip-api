package com.sajidtech.easytrip.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class BookingRequest {

    @NotBlank(message = "Pickup location is required")
    @Size(min = 3, max = 50, message = "Pickup location must be between 3 and 50 characters")
    @Pattern(
            regexp = "^[A-Za-z0-9,\\- ]+$",
            message = "Pickup location contains invalid characters"
    )
    private String pickup;

    @NotBlank(message = "Destination is required")
    @Size(min = 3, max = 50, message = "Destination must be between 3 and 50 characters")
    @Pattern(
            regexp = "^[A-Za-z0-9,\\- ]+$",
            message = "Destination contains invalid characters"
    )
    private String destination;

    @NotNull(message = "Trip distance is required")
    @Positive(message = "Trip distance must be greater than zero")
    @DecimalMax(value = "1000.0", message = "Trip distance looks unrealistic")
    private Double tripDistanceInKm;
}
