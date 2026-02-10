package com.sajidtech.easytrip.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.sajidtech.easytrip.enums.Gender;
import com.sajidtech.easytrip.enums.Status;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "driverId",
        "name",
        "age",
        "email",
        "mobileNo",
        "license",
        "experience",
        "gender",
        "status",
        "createProfileAt",
        "lastUpdateAt"
})
public class DriverResponse {

    private Integer driverId;
    private String name;
    private Byte age;
    private String email;
    private String mobileNo;
    private String license;
    private Byte experience;
    private Gender gender;
    private Status status;
    private Date createProfileAt;
    private Date lastUpdateAt;
}
