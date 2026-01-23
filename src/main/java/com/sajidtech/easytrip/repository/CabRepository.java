package com.sajidtech.easytrip.repository;


import com.sajidtech.easytrip.Enum.Status;
import com.sajidtech.easytrip.model.Cab;
import com.sajidtech.easytrip.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CabRepository extends JpaRepository<Cab, Integer> {

    @Query("select c from Cab c where c.available = true and c.status = com.sajidtech.easytrip.Enum.Status.ACTIVE order by rand() limit 1")
    Optional<Cab> getAvailableCab();

    @Query("select c from Cab c where c.available = true")
    List<Cab> getAllAvailableCab();

    @Query("SELECT d FROM Driver d JOIN d.cab c WHERE c.cabId = :cabId")
    Driver getDriverByCabId(@Param("cabId") int cabId);

    @Query("select c from Cab c where c.status = :status")
    List<Cab> findByStatus(@Param("status") Status status);

    @Query("select c from Cab c where c.available = false")
    List<Cab> getUnavailableCab();
}
