package com.tuhospedaje.backend.service.impl;

import com.tuhospedaje.backend.entity.Hotel;
import com.tuhospedaje.backend.exception.ResourceNotFoundException;
import com.tuhospedaje.backend.repository.IHotelRepository;
import com.tuhospedaje.backend.service.IHotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HotelServiceImpl implements IHotelService {
    @Autowired
    private IHotelRepository hotelRepository;

    @Override
    public Hotel save(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    @Override
    public Optional<Hotel> findById(Long id) {
        return hotelRepository.findById(id);
    }

    @Override
    public void update(Hotel hotel) {
        if (!hotelRepository.existsById(hotel.getId())) {
            throw new ResourceNotFoundException("Hotel no encontrado con ID: " + hotel.getId());
        }
        hotelRepository.save(hotel);
    }

    @Override
    public void delete(Long id) {
        if (!hotelRepository.existsById(id)) {
            throw new ResourceNotFoundException("Hotel no encontrado con ID: " + id);
        }
        hotelRepository.deleteById(id);
    }

    @Override
    public List<Hotel> findAll() {
        return hotelRepository.findAll();
    }

    @Override
    public Optional<Hotel> findByName(String name) {
        return hotelRepository.findByName(name);
    }
}

