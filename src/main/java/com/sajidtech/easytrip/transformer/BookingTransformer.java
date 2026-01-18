package com.sajidtech.easytrip.transformer;

import com.sajidtech.easytrip.Enum.TripStatus;
import com.sajidtech.easytrip.dto.request.BookingRequest;
import com.sajidtech.easytrip.dto.response.BookingResponse;
import com.sajidtech.easytrip.model.Booking;
import com.sajidtech.easytrip.model.Cab;
import com.sajidtech.easytrip.model.Customer;
import com.sajidtech.easytrip.model.Driver;
import lombok.Data;
import org.springframework.orm.jpa.vendor.Database;

import java.util.Date;

public class BookingTransformer {

    public static Booking bookingRequestToBooking(BookingRequest bookingRequest, double cabPerKmRate){
        return Booking.builder()
                .pickup(bookingRequest.getPickup())
                .destination(bookingRequest.getDestination())
                .tripDistanceInKm(bookingRequest.getTripDistanceInKm())
                .tripStatus(TripStatus.IN_PROGRESS)
                .billAmount(bookingRequest.getTripDistanceInKm() * cabPerKmRate)
                .build();
    }

    public static BookingResponse bookingToBookingResponse(Booking booking, Cab cab, Driver driver, Customer customer){
        return BookingResponse.builder()
                .pickup(booking.getPickup())
                .destination(booking.getDestination())
                .tripDistanceInKm(booking.getTripDistanceInKm())
                .tripStatus(booking.getTripStatus())
                .billAmount(booking.getBillAmount())
                .bookedAt(booking.getBookedAt())
                .lastUpdateAt(booking.getLastUpdateAt())
                .customerResponse(CustomerTransformer.CustomerToCustomerResponse(customer))
                .cabResponse(CabTransformer.CabToCabResponse(cab, driver))
                .build();
    }
}
