package com.tuhospedaje.backend.dto;

import com.tuhospedaje.backend.entity.Image;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageDTO {
    private Long id;
    private String url;
    private boolean featured;
    private Integer position;

    public static ImageDTO fromEntity(Image image) {
        return ImageDTO.builder()
                .id(image.getId())
                .url(image.getUrl())
                .featured(image.isFeatured())
                .position(image.getPosition())
                .build();
    }
}
