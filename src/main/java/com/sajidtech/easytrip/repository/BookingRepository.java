package com.sajidtech.easytrip.repository;

import com.sajidtech.easytrip.Enum.TripStatus;
import com.sajidtech.easytrip.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Integer> {

    @Query(value = "select driver_id from booking where booking_id = :id", nativeQuery = true)
    Optional<Integer> getDriverIdByBookingId(@Param("id") int bookingId);

    @Query("select b from Booking b where b.tripStatus= :status")
    List<Booking> findByTripStatus(@Param("status") TripStatus tripStatus);
}
