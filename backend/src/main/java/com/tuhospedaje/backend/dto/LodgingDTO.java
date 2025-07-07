package com.tuhospedaje.backend.dto;

import com.tuhospedaje.backend.entity.Image;
import com.tuhospedaje.backend.entity.Lodging;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
    private List<ImageDTO> images;
    private Long propertyTypeId;

    public static LodgingDTO fromEntity(Lodging lodging) {
        List<ImageDTO> imageDTOs = lodging.getImages()
                .stream()
                .map(ImageDTO::fromEntity)
                .collect(Collectors.toList());

        return LodgingDTO.builder()
                .id(lodging.getId())
                .name(lodging.getName())
                .description(lodging.getDescription())
                .rating(lodging.getRating())
                .address(lodging.getAddress())
                .city(lodging.getCity())
                .country(lodging.getCountry())
                .phoneNumber(lodging.getPhoneNumber())
                .email(lodging.getEmail())
                .images(imageDTOs)
                .propertyTypeId(lodging.getLodgingType().getId())
                .build();
    }

    public Lodging toEntity() {
        Lodging lodging = new Lodging();
        lodging.setId(this.id);
        lodging.setName(this.name);
        lodging.setDescription(this.description);
        lodging.setRating(this.rating);
        lodging.setAddress(this.address);
        lodging.setCity(this.city);
        lodging.setCountry(this.country);
        lodging.setPhoneNumber(this.phoneNumber);
        lodging.setEmail(this.email);

        if (this.getImages() != null) {
            var images = this.getImages().stream()
                    .map(imageDTO -> {
                        var image = new Image();
                        image.setUrl(imageDTO.getUrl());
                        image.setFeatured(imageDTO.isFeatured());
                        image.setPosition(imageDTO.getPosition());
                        image.setLodging(lodging);
                        return image;
                    }).toList();

            lodging.setImages(images);
        }

        return lodging;
    }
}
