package com.sajidtech.easytrip.exception;

public class InvalidOldPasswordException extends RuntimeException {
    public InvalidOldPasswordException(String msg) {
        super(msg);
    }
}
