package com.sajidtech.easytrip.repository;

import com.sajidtech.easytrip.model.Cab;
import com.sajidtech.easytrip.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepository extends JpaRepository<Driver,Integer> {

    @Query(value = "select * from driver where cab_id = :availableCabId", nativeQuery = true)
    Driver availableCabDriver(@Param("availableCabId") int availableCabId);
}
