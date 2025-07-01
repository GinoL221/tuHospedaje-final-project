package com.tuhospedaje.backend.service.impl;

import com.tuhospedaje.backend.dto.BookingDTO;
import com.tuhospedaje.backend.entity.Booking;
import com.tuhospedaje.backend.enums.BookingStatusEnum;
import com.tuhospedaje.backend.exception.ResourceNotFoundException;
import com.tuhospedaje.backend.repository.IBookingRepository;
import com.tuhospedaje.backend.repository.IRoomRepository;
import com.tuhospedaje.backend.repository.IUserRepository;
import com.tuhospedaje.backend.service.IBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        var room = roomRepository.findById(dto.getRoomId())
                .orElseThrow(() -> new ResourceNotFoundException("Habitación no encontrada"));

        Booking bookingEntity = new Booking();
        bookingEntity.setUser(user);
        bookingEntity.setRoom(room);

        // Convertir fechas de String a LocalDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate checkInDate = LocalDate.parse(dto.getCheckInDate().toString(), formatter);
        LocalDate checkOutDate = LocalDate.parse(dto.getCheckOutDate().toString(), formatter);
        bookingEntity.setCheckInDate(checkInDate);
        bookingEntity.setCheckOutDate(checkOutDate);

        bookingEntity.setNumberOfGuests(dto.getNumberOfGuests());
        bookingEntity.setTotalPrice(dto.getTotalPrice());
        bookingEntity.setStatus(BookingStatusEnum.valueOf(dto.getStatus()));

        bookingEntity.setCreationDate(LocalDateTime.now());

        var saved = bookingRepository.save(bookingEntity);

        return BookingDTO.builder()
                .id(saved.getId())
                .userId(user.getId())
                .roomId(saved.getRoom().getId())
                .checkInDate(saved.getCheckInDate())
                .checkOutDate(saved.getCheckOutDate())
                .numberOfGuests(saved.getNumberOfGuests())
                .totalPrice(saved.getTotalPrice())
                .status(String.valueOf(saved.getStatus()))
                .creationDate(saved.getCreationDate())
                .build();
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

    private Booking mapToEntity(BookingDTO dto) {
        Booking booking = new Booking();

        if (dto.getId() != null) {
            booking.setId(dto.getId());
        }

        var user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + dto.getUserId()));
        booking.setUser(user);

        var room = roomRepository.findById(dto.getRoomId())
                .orElseThrow(() -> new ResourceNotFoundException("Habitación no encontrada con ID: " + dto.getRoomId()));
        booking.setRoom(room);

        booking.setCheckInDate(dto.getCheckInDate());
        booking.setCheckOutDate(dto.getCheckOutDate());
        booking.setNumberOfGuests(dto.getNumberOfGuests());
        booking.setTotalPrice(dto.getTotalPrice());
        booking.setStatus(BookingStatusEnum.valueOf(dto.getStatus()));

        booking.setCreationDate(LocalDateTime.now());

        return booking;
    }
}
