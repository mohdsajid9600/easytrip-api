package com.sajidtech.easytrip.repository;


import com.sajidtech.easytrip.enums.Status;
import com.sajidtech.easytrip.model.Cab;
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
public interface CabRepository extends JpaRepository<Cab, Integer> {

    @Query("select c from Cab c where c.isAvailable = true and c.status = com.sajidtech.easytrip.enums.Status.ACTIVE order by rand() limit 1")
    Optional<Cab> getAvailableCab();

    @Query("select c from Cab c where c.isAvailable = true")
    Page<Cab> getAllAvailableCab(Pageable pageable);

    @Query("SELECT d FROM Driver d JOIN d.cab c WHERE c.cabId = :cabId")
    Driver getDriverByCabId(@Param("cabId") Integer cabId);

    @Query("select c from Cab c where c.status = :status")
    Page<Cab> findByStatus(@Param("status") Status status, Pageable pageable);

    @Query("select c from Cab c where c.isAvailable = false")
    Page<Cab> getUnavailableCab(Pageable pageable);
}
