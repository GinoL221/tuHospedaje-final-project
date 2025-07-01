package com.tuhospedaje.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class LodgingTypeDTO {
    private Long id;
    private String name;

    public LodgingTypeDTO() {
    }
}
