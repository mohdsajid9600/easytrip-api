package com.sajidtech.easytrip.repository;

import com.sajidtech.easytrip.enums.Gender;
import com.sajidtech.easytrip.enums.Status;
import com.sajidtech.easytrip.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {


    Page<Customer> findByGenderAndAge(Gender gender, Byte age, Pageable pageable);

    @Query("select c from Customer c where c.age > :age")
    Page<Customer> getAllGreaterThenAge(@Param("age") Byte age, Pageable pageable);

    @Query("SELECT c FROM Customer c JOIN c.booking b WHERE b.bookingId = :bookingId")
    Customer findByBookingId(@Param("bookingId") Integer bookingId);

    @Query("select c from Customer c where c.status =:status")
    Page<Customer> findByStatus(@Param("status") Status status, Pageable pageable);

    @Query("SELECT c FROM Customer c JOIN c.booking b WHERE b.bookingId = :bookingId")
    Customer findCustomerByBookingId(@Param("bookingId") Integer bookingId);

    @Query("select c from Customer c where c.email =:email")
    Optional<Customer> findByEmail(@Param("email") String email);
}
