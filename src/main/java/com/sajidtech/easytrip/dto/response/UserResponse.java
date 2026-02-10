package com.sajidtech.easytrip.dto.response;



import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.sajidtech.easytrip.enums.Role;
import com.sajidtech.easytrip.enums.Status;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "email",
        "role",
        "profileStatus"
})
public class UserResponse {


    private String email;
    private Role role;
    private Status profileStatus;
}
