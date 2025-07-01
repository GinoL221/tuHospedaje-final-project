package com.tuhospedaje.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "lodging_type")
public class LodgingType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    public LodgingType() {
    }

    public LodgingType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}