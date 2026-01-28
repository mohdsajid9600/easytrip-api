package com.sajidtech.easytrip.dto.request;

import com.sajidtech.easytrip.enums.Gender;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CustomerRequest {

    @NotBlank(message = "Name is required")
    private String name;
    @Min(value = 15, message = "Age must be at least 18")
    @Max(value = 90, message = "Age not exceed more then 90")
    private int age;
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be a valid email address")
    private String email;
    @NotNull(message = "Gender is required")
    private Gender gender;
}
