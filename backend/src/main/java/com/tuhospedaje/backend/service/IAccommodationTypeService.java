package com.tuhospedaje.backend.service;

import com.tuhospedaje.backend.entity.AccommodationType;
import com.tuhospedaje.backend.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface IAccommodationTypeService {
    AccommodationType save(AccommodationType type);

    AccommodationType update(AccommodationType type) throws ResourceNotFoundException;

    void delete(Long id) throws ResourceNotFoundException;

    Optional<AccommodationType> findById(Long id);

    List<AccommodationType> findAll();

    Optional<AccommodationType> findByName(String name);
}
