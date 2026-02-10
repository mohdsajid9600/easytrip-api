package com.sajidtech.easytrip.model;

import com.sajidtech.easytrip.enums.Gender;
import com.sajidtech.easytrip.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.id.IntegralDataTypeHolder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="drivers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer driveId;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Byte age;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(unique = true, nullable = false)
    private String mobileNo;
    @Column(unique = true, nullable = false)
    private String license;
    @Column(nullable = false)
    private Byte experience;
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Enumerated(value = EnumType.STRING)
    private Status status;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "driver_id")
    private List<Booking> booking = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cab_id")
    private Cab cab;

    @CreationTimestamp
    @Column(updatable = false)
    private Date createProfileAt;

    @UpdateTimestamp
    private Date lastUpdateAt;
}
