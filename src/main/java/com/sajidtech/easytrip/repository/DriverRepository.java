package com.sajidtech.easytrip.repository;

import com.sajidtech.easytrip.enums.Status;
import com.sajidtech.easytrip.model.Driver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver,Integer> {

    @Query(value = "select * from drivers where cab_id = :availableCabId", nativeQuery = true)
    Driver availableCabDriver(@Param("availableCabId") Integer availableCabId);

    @Query("SELECT d FROM Driver d JOIN d.booking b WHERE b.bookingId = :bookingId")
    Driver findDriverByBookingId(Integer bookingId);

    @Query("select d from Driver d where d.status =:status")
    Page<Driver> findByStatus(@Param("status") Status status, Pageable pageable);

    @Query("select d from Driver d where d.email =:email")
    Optional<Driver> findByEmail(@Param("email") String email);
}
