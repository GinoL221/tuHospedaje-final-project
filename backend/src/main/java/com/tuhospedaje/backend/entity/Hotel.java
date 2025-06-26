package com.tuhospedaje.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "hotels")
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price_per_night", nullable = false)
    private Double pricePerNight;

    @Column(name = "rating")
    private Double rating;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "image")
    private String image;

    @ManyToOne
    @JoinColumn(name = "accommodation_type_id", nullable = false)
    private AccommodationType accommodationType;

    public Hotel() {
    }
}
