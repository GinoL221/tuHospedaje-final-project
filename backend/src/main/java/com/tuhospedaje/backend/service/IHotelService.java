package com.tuhospedaje.backend.service;

import com.tuhospedaje.backend.entity.Hotel;
import com.tuhospedaje.backend.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface IHotelService {
    Hotel save(Hotel hotel);

    Optional<Hotel> findById(Long id);

    void update(Hotel hotel) throws ResourceNotFoundException;

    void delete(Long id) throws ResourceNotFoundException;

    List<Hotel> findAll();

    Optional<Hotel> findByName(String name);
}
