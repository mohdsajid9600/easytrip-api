package com.sajidtech.easytrip.emails;

import com.sajidtech.easytrip.enums.TripStatus;
import com.sajidtech.easytrip.dto.response.BookingResponse;

public class CustomerEmailFormate {


    public static String getEmailTemplate(TripStatus status,BookingResponse booking){

        return switch (status) {
            case IN_PROGRESS -> bookingConfirmationTemplate(booking);
            case COMPLETED -> bookingCompletedTemplate(booking);
            case CANCELLED -> bookingCancelledTemplate(booking);
            default -> throw new IllegalArgumentException("Invalid booking status");
        };
    }


    // ===============================
    // BOOKING CONFIRMATION EMAIL
    // ===============================
    public static String bookingConfirmationTemplate(BookingResponse booking) {

        return "Dear " + booking.getCustomerResponse().getName() + ",\n\n" +

                "Thank you for choosing EasyTrip!\n\n" +

                "Your cab booking has been successfully confirmed. Please find your trip details below:\n\n" +

                "========================================\n" +
                "Passenger Name : " + booking.getCustomerResponse().getName() + "\n" +
                "Pickup Point   : " + booking.getPickup() + "\n" +
                "Drop Point     : " + booking.getDestination() + "\n" +
                "Total Fare     : ₹" + booking.getBillAmount() + "\n\n" +

                "Cab Model      : " + booking.getCabResponse().getCabModel() + "\n" +
                "Cab Number     : " + booking.getCabResponse().getCabNumber() + "\n\n" +

                "Driver Name    : " + booking.getCabResponse().getDriverResponse().getName() + "\n" +
                "Driver Email   : " + booking.getCabResponse().getDriverResponse().getEmail() + "\n" +
                "========================================\n\n" +

                "Your cab will reach your pickup location shortly.\n\n" +

                "We wish you a safe and comfortable journey with EasyTrip.\n\n" +

                footer();
    }



    // ===============================
    // BOOKING COMPLETED EMAIL
    // ===============================
    public static String bookingCompletedTemplate(BookingResponse booking) {

        return "Dear " + booking.getCustomerResponse().getName() + ",\n\n" +

                "Thank you for choosing EasyTrip!\n\n" +

                "We are happy to inform you that your cab booking has been successfully completed.\n\n" +

                "========================================\n" +
                "Passenger Name : " + booking.getCustomerResponse().getName() + "\n" +
                "Pickup Point   : " + booking.getPickup() + "\n" +
                "Drop Point     : " + booking.getDestination() + "\n" +
                "Total Fare     : ₹" + booking.getBillAmount() + "\n\n" +

                "Cab Model      : " + booking.getCabResponse().getCabModel() + "\n" +
                "Cab Number     : " + booking.getCabResponse().getCabNumber() + "\n\n" +

                "Driver Name    : " + booking.getCabResponse().getDriverResponse().getName() + "\n" +
                "Driver Email   : " + booking.getCabResponse().getDriverResponse().getEmail() + "\n" +
                "========================================\n\n" +

                "We hope you had a comfortable and pleasant journey.\n\n" +

                "Thank you for riding with EasyTrip. We look forward to serving you again!\n\n" +

                footer();
    }


    // ===============================
    // BOOKING CANCELLED EMAIL
    // ===============================
    public static String bookingCancelledTemplate(BookingResponse booking) {

        return "Dear " + booking.getCustomerResponse().getName() + ",\n\n" +

                "We regret to inform you that your cab booking has been cancelled.\n\n" +

                "========================================\n" +
                "Passenger Name : " + booking.getCustomerResponse().getName() + "\n" +
                "Pickup Point   : " + booking.getPickup() + "\n" +
                "Drop Point     : " + booking.getDestination() + "\n" +
                "========================================\n\n" +

                "If any amount was paid, the refund (if applicable) will be processed as per our refund policy.\n\n" +

                "We apologize for any inconvenience caused.\n\n" +

                "If you have any questions or need assistance, feel free to contact our support team.\n\n" +

                footer();
    }


    public static String getSubject(TripStatus status) {

        return switch (status) {
            case IN_PROGRESS -> "Your EasyTrip Booking is Confirmed";
            case COMPLETED -> "Trip Completed – Thank You for Riding with EasyTrip";
            case CANCELLED -> "EasyTrip Booking Cancelled";
            default -> "EasyTrip Notification";
        };
    }


    private static String footer() {
        return "\n\nRegards,\n" +
                "EasyTrip Support Team\n" +
                "Customer Care: +91-90000-00000\n" +
                "Email: support@easytrip.com";
    }
}
