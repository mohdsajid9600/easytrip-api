package com.sajidtech.easytrip.model;


import com.sajidtech.easytrip.Enum.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="cab")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cab {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cabId;

    @Column(unique = true, nullable = false)
    private String cabNumber;
    @Column(nullable = false)
    private String cabModel;
    @Column(nullable = false)
    private double perKmRate;
    private boolean available;
    @Enumerated(value = EnumType.STRING)
    private Status status;

}
