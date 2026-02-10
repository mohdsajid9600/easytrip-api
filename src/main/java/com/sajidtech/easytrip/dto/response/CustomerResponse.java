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
        "customerId",
        "name",
        "age",
        "email",
        "mobileNo",
        "gender",
        "status",
        "createProfileAt",
        "lastUpdateAt"
})
public class CustomerResponse {

    private Integer customerId;
    private String name;
    private Byte age;
    private String email;
    private String mobileNo;
    private Gender gender;
    private Status status;
    private Date createProfileAt;
    private Date lastUpdateAt;
}
