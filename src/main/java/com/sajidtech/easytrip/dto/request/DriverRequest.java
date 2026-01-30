package com.sajidtech.easytrip.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DriverRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @Min(value = 18, message = "Age must be at least 18")
    @Max(value = 60, message = "Age not exceed more then 60")
    private int age;
}
