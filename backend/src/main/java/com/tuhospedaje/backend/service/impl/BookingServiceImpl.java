package com.tuhospedaje.backend.service.impl;

import com.tuhospedaje.backend.dto.BookingDTO;
import com.tuhospedaje.backend.entity.Booking;
import com.tuhospedaje.backend.exception.ResourceNotFoundException;
import com.tuhospedaje.backend.repository.IBookingRepository;
import com.tuhospedaje.backend.service.IBookingService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements IBookingService {
    private IBookingRepository bookingRepository;

    public BookingServiceImpl(IBookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public BookingDTO save(BookingDTO bookingDTO) {
        Booking booking = mapToEntity(bookingDTO);
        Booking saved = bookingRepository.save(booking);
        return mapToDTO(saved);
    }

    @Override
    public Optional<BookingDTO> findById(Long id) {
        return bookingRepository.findById(id).map(this::mapToDTO);
    }

    @Override
    public BookingDTO update(BookingDTO bookingDTO) throws ResourceNotFoundException {
        if (!bookingRepository.existsById(bookingDTO.getId())) {
            throw new ResourceNotFoundException("Reserva no encontrada con ID: " + bookingDTO.getId());
        }
        Booking updated = bookingRepository.save(mapToEntity(bookingDTO));
        return mapToDTO(updated);
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        if (!bookingRepository.existsById(id)) {
            throw new ResourceNotFoundException("Reserva no encontrada con ID: " + id);
        }
        bookingRepository.deleteById(id);
    }

    @Override
    public List<BookingDTO> findAll() {
        return bookingRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Conversión Entity -> DTO
    private BookingDTO mapToDTO(Booking booking) {
        return BookingDTO.builder()
                .id(booking.getId())
                .userId(booking.getUser().getId())
                .hotelId(booking.getHotel().getId())
                .checkInDate(booking.getCheckInDate())
                .checkOutDate(booking.getCheckOutDate())
                .numberOfGuests(booking.getNumberOfGuests())
                .totalPrice(booking.getTotalPrice())
                .status(booking.getStatus())
                .build();
    }

    // Conversión DTO -> Entity
    private Booking mapToEntity(BookingDTO dto) {
        Booking booking = new Booking();
        booking.setId(dto.getId());
        // Acá deberías obtener las entidades Hotel y User desde sus respectivos repositorios
        // y setearlas con booking.setHotel(hotel) y booking.setUser(user)
        booking.setCheckInDate(dto.getCheckInDate());
        booking.setCheckOutDate(dto.getCheckOutDate());
        booking.setNumberOfGuests(dto.getNumberOfGuests());
        booking.setTotalPrice(dto.getTotalPrice());
        booking.setStatus(dto.getStatus());
        return booking;
    }
}
