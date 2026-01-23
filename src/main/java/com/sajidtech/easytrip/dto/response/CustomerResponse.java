package com.sajidtech.easytrip.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.sajidtech.easytrip.Enum.Gender;
import com.sajidtech.easytrip.Enum.Status;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "name",
        "age",
        "email",
        "gender",
        "status"
})
public class CustomerResponse {

    private String name;
    private Integer age;
    private String email;
    private Gender gender;
    private Status status;
}
