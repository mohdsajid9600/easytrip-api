package com.sajidtech.easytrip.repository;

import com.sajidtech.easytrip.model.Cab;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CabRepository extends JpaRepository<Cab, Integer> {

    @Query("select c from Cab c where c.available = true order by rand() limit 1")
    Optional<Cab> getAvailableCab();

    @Query("select c from Cab c where c.available = true")
    List<Cab> getAllAvailableCab();
}
