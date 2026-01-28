package com.sajidtech.easytrip.dto.request;

import com.sajidtech.easytrip.enums.Role;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class SignupRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

//    @Pattern(
//            regexp = "^(?=.*[0-9])(?=.*[a-zA-Z]).{6,}$",
//            message = "Password must contain letters and numbers"
//    )
//    private String password;

    @NotNull(message = "Role is required")
    private Role role;
}
