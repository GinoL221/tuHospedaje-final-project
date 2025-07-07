package com.tuhospedaje.backend.service.impl;

import com.tuhospedaje.backend.dto.BookingDTO;
import com.tuhospedaje.backend.entity.Booking;
import com.tuhospedaje.backend.enums.BookingStatusEnum;
import com.tuhospedaje.backend.exception.BadRequestException;
import com.tuhospedaje.backend.exception.ResourceNotFoundException;
import com.tuhospedaje.backend.repository.IBookingRepository;
import com.tuhospedaje.backend.repository.IRoomRepository;
import com.tuhospedaje.backend.repository.IUserRepository;
import com.tuhospedaje.backend.service.IBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements IBookingService {
    private final IBookingRepository bookingRepository;
    private final IUserRepository userRepository;
    private final IRoomRepository roomRepository;

    @Autowired
    public BookingServiceImpl(IBookingRepository bookingRepository, IUserRepository userRepository,
                              IRoomRepository roomRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public BookingDTO save(BookingDTO dto) {
        var user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + dto.getUserId()));

        var room = roomRepository.findById(dto.getRoomId())
                .orElseThrow(() -> new ResourceNotFoundException("Habitación no encontrada con ID: " + dto.getRoomId()));

        boolean overlaps = bookingRepository.existsByRoomAndDates(room, dto.getCheckInDate(), dto.getCheckOutDate());
        if (overlaps) {
            throw new BadRequestException("La habitación ya está reservada en las fechas indicadas.");
        }

        Booking bookingEntity = new Booking();
        bookingEntity.setUser(user);
        bookingEntity.setRoom(room);
        bookingEntity.setCheckInDate(dto.getCheckInDate());
        bookingEntity.setCheckOutDate(dto.getCheckOutDate());
        bookingEntity.setNumberOfGuests(dto.getNumberOfGuests());
        bookingEntity.setTotalPrice(dto.getTotalPrice());

        try {
            bookingEntity.setStatus(BookingStatusEnum.valueOf(dto.getStatus()));
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Estado de reserva inválido: " + dto.getStatus());
        }

        bookingEntity.setCreationDate(LocalDateTime.now());

        var saved = bookingRepository.save(bookingEntity);
        return mapToDTO(saved);
    }

    @Override
    public Optional<BookingDTO> findById(Long id) {
        return bookingRepository.findById(id).map(this::mapToDTO);
    }

    @Override
    public BookingDTO update(BookingDTO bookingDTO) {
        Booking existing = bookingRepository.findById(bookingDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada con ID: " + bookingDTO.getId()));

        existing.setCheckInDate(bookingDTO.getCheckInDate());
        existing.setCheckOutDate(bookingDTO.getCheckOutDate());
        existing.setNumberOfGuests(bookingDTO.getNumberOfGuests());
        existing.setTotalPrice(bookingDTO.getTotalPrice());

        try {
            existing.setStatus(BookingStatusEnum.valueOf(bookingDTO.getStatus()));
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Estado de reserva inválido: " + bookingDTO.getStatus());
        }

        var user = userRepository.findById(bookingDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + bookingDTO.getUserId()));
        existing.setUser(user);

        var room = roomRepository.findById(bookingDTO.getRoomId())
                .orElseThrow(() -> new ResourceNotFoundException("Habitación no encontrada con ID: " + bookingDTO.getRoomId()));
        existing.setRoom(room);

        Booking updated = bookingRepository.save(existing);
        return mapToDTO(updated);
    }

    @Override
    public void delete(Long id) {
        try {
            bookingRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Reserva no encontrada con ID: " + id);
        }
    }

    @Override
    public List<BookingDTO> findAll() {
        return bookingRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private BookingDTO mapToDTO(Booking booking) {
        return BookingDTO.builder()
                .id(booking.getId())
                .userId(booking.getUser().getId())
                .roomId(booking.getRoom().getId())
                .checkInDate(booking.getCheckInDate())
                .checkOutDate(booking.getCheckOutDate())
                .numberOfGuests(booking.getNumberOfGuests())
                .totalPrice(booking.getTotalPrice())
                .status(String.valueOf(booking.getStatus()))
                .creationDate(booking.getCreationDate())
                .build();
    }
}
