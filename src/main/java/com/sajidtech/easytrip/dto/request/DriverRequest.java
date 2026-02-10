package com.sajidtech.easytrip.dto.request;

import com.sajidtech.easytrip.enums.Gender;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class DriverRequest {

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    @Pattern(
            regexp = "^[A-Za-z ]+$",
            message = "Name must contain only letters and spaces"
    )
    private String name;

    @NotNull(message = "Age is required")
    @Min(value = 18, message = "Age must be at least 18")
    @Max(value = 60, message = "Age cannot exceed 60")
    private Byte age;

    @NotBlank(message = "Mobile number is required")
    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid mobile number")
    private String mobileNo;

    @NotBlank(message = "Driving license is required")
    @Size(min = 5, max = 20, message = "License number looks invalid")
    @Pattern(
            regexp = "^[A-Z]{2}[0-9]{2}[0-9]{4}[0-9]{7}$",
            message = "Invalid driving license format"
    )
    private String license;

    @NotNull(message = "Experience is required")
    @Min(value = 0, message = "Experience cannot be negative")
    @Max(value = 40, message = "Experience looks unrealistic")
    private Byte experience;

    @NotNull(message = "Gender is required")
    private Gender gender;
}
