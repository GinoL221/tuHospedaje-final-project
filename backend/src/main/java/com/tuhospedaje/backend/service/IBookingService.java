package com.tuhospedaje.backend.service;

import com.tuhospedaje.backend.dto.BookingDTO;
import com.tuhospedaje.backend.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface IBookingService {
    BookingDTO save(BookingDTO bookingDTO);

    Optional<BookingDTO> findById(Long id);

    BookingDTO update(BookingDTO bookingDTO) throws ResourceNotFoundException;

    void delete(Long id) throws ResourceNotFoundException;

    List<BookingDTO> findAll();
}
