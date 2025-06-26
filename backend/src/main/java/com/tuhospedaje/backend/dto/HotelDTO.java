package com.tuhospedaje.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class HotelDTO {
    private Long id;
    private String name;
    private String description;
    private Double pricePerNight;
    private Double rating;
    private String address;
    private String city;
    private String country;
    private String phoneNumber;
    private String email;
    private String image;
    private String accommodationType;

    public HotelDTO() {
    }
}
