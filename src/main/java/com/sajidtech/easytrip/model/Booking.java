package com.sajidtech.easytrip.model;

import com.sajidtech.easytrip.enums.TripStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name="bookings")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bookingId;
    @Column(nullable = false)
    private String pickup;
    @Column(nullable = false)
    private String destination;
    @Column(nullable = false)
    private Double tripDistanceInKm;
    @Column(nullable = false)
    private Double cabRateAtBooking;
    @Column(nullable = false)
    private Double billAmount;

    @Enumerated(value = EnumType.STRING)
    private TripStatus tripStatus;

    @CreationTimestamp
    @Column(updatable = false)
    private Date bookedAt;
    @UpdateTimestamp
    private Date lastUpdateAt;

}
