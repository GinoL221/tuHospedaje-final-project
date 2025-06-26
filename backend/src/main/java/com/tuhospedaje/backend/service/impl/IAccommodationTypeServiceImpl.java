package com.tuhospedaje.backend.service.impl;

import com.tuhospedaje.backend.entity.AccommodationType;
import com.tuhospedaje.backend.exception.ResourceNotFoundException;
import com.tuhospedaje.backend.service.IAccommodationTypeService;

import java.util.List;
import java.util.Optional;

public class IAccommodationTypeServiceImpl implements IAccommodationTypeService {
    @Override
    public AccommodationType save(AccommodationType type) {
        return null;
    }

    @Override
    public AccommodationType update(AccommodationType type) throws ResourceNotFoundException {
        return null;
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {

    }

    @Override
    public Optional<AccommodationType> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<AccommodationType> findAll() {
        return List.of();
    }

    @Override
    public Optional<AccommodationType> findByName(String name) {
        return Optional.empty();
    }
}
