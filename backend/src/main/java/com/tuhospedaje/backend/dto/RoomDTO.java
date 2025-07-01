package com.tuhospedaje.backend.dto;

import com.tuhospedaje.backend.enums.RoomTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class RoomDTO {
    private Long id;
    private Long lodgingId;
    private RoomTypeEnum roomType;
    private String roomNumber;
    private Boolean available;
    private Double price;

    public RoomDTO() {
    }
}
