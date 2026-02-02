package com.sajidtech.easytrip.repository;

import com.sajidtech.easytrip.enums.TripStatus;
import com.sajidtech.easytrip.model.Booking;
import com.sajidtech.easytrip.model.Customer;
import com.sajidtech.easytrip.model.Driver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Integer> {

    @Query("select b from Booking b where b.tripStatus= :status")
    Page<Booking> findByTripStatus(@Param("status") TripStatus tripStatus,Pageable pageable);

    @Query("SELECT b FROM Customer c JOIN c.booking b WHERE c = :customer")
    Page<Booking> findBookingsByCustomer(Customer customer, Pageable pageable);


    @Query("SELECT b FROM Driver d JOIN d.booking b WHERE d = :driver")
    Page<Booking> findBookingsByDriver(Driver driver, Pageable pageable);
}
