package com.tuhospedaje.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class LodgingDTO {
    private Long id;
    private String name;
    private String description;
    private Double rating;
    private String address;
    private String city;
    private String country;
    private String phoneNumber;
    private String email;
    private String image;
    private Long propertyTypeId;

    public LodgingDTO() {
    }
}
