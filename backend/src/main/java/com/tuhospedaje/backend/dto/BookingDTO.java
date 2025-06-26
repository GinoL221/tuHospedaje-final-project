package com.tuhospedaje.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class BookingDTO {
    private Long id;
    private Long userId;
    private Long hotelId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Integer numberOfGuests;
    private Double totalPrice;
    private String status;

    public BookingDTO() {
    }
}
