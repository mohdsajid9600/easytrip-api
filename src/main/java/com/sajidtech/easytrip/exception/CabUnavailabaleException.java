package com.sajidtech.easytrip.exception;

import com.sajidtech.easytrip.dto.response.BookingResponse;

public class CabUnavailabaleException extends RuntimeException {
    public CabUnavailabaleException(String message) {
        super(message);
    }
}
