package com.sajidtech.easytrip.model;


import com.sajidtech.easytrip.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="cabs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cab {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cabId;

    @Column(unique = true, nullable = false)
    private String cabNumber;
    @Column(nullable = false)
    private String cabModel;
    @Column(nullable = false)
    private Double perKmRate;
    private Boolean isAvailable;
    @Enumerated(value = EnumType.STRING)
    private Status status;

}
