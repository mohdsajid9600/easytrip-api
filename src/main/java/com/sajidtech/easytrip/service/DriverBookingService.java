package com.sajidtech.easytrip.service;

public interface DriverBookingService extends BookingServiceQueries{

    void completeBookingByDriver(String email);
}
