package com.sajidtech.easytrip.dto.request;

import com.sajidtech.easytrip.Enum.Gender;

import lombok.Data;

@Data
public class CustomerRequest {

    private String name;
    private int age;
    private String email;
    private Gender gender;
}
