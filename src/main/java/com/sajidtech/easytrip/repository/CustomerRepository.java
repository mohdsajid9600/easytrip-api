package com.sajidtech.easytrip.repository;

import com.sajidtech.easytrip.Enum.Gender;
import com.sajidtech.easytrip.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {


    List<Customer> findByGenderAndAge(Gender gender, int age);

    @Query("select c from Customer c where c.age > :age")
    List<Customer> getAllGreaterThenAge(@Param("age") int age);

    @Query("SELECT c FROM Customer c JOIN c.booking b WHERE b.bookingId = :bookingId")
    Customer findByBookingId(@Param("bookingId") int bookingId);
}
