package com.tuhospedaje.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.tuhospedaje.backend.enums.RoomTypeEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "lodging_id")
    @JsonBackReference
    private Lodging lodging;

    @Enumerated(EnumType.STRING)
    @Column(name = "room_type", nullable = false)
    private RoomTypeEnum roomType;

    @Column(nullable = false)
    private String roomNumber;

    @Column(nullable = false)
    private Boolean available = true;

    @Column(nullable = false)
    private Double price;

    public Room() {
    }
}
