package com.sajidtech.easytrip.dto.request;

import com.sajidtech.easytrip.enums.Gender;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CustomerRequest {

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    @Pattern(
            regexp = "^[A-Za-z ]+$",
            message = "Name must contain only letters and spaces"
    )
    private String name;

    @Min(value = 15, message = "Age must be at least 18")
    @Max(value = 90, message = "Age not exceed more then 90")
    private Byte age;

    @NotNull(message = "Mobile no. is required")
    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid mobile number")
    private String mobileNo;

    @NotNull(message = "Gender is required")
    private Gender gender;


}
