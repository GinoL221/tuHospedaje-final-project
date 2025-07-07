package com.tuhospedaje.backend.controller;

import com.tuhospedaje.backend.dto.BookingDTO;
import com.tuhospedaje.backend.exception.ResourceNotFoundException;
import com.tuhospedaje.backend.service.IBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reservas")
public class BookingController {
    private final IBookingService bookingService;

    @Autowired
    public BookingController(IBookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<BookingDTO> save(@RequestBody BookingDTO bookingDTO) {
        if (bookingDTO.getId() != null) {
            return ResponseEntity.badRequest().build();
        }

        BookingDTO saved = bookingService.save(bookingDTO);
        return ResponseEntity.status(201).body(saved);
    }


    @PutMapping
    public ResponseEntity<BookingDTO> update(@RequestBody BookingDTO bookingDTO) {
        ResponseEntity<BookingDTO> response;

        if (bookingService.findById(bookingDTO.getId()).isPresent()) {
            response = ResponseEntity.ok(bookingService.update(bookingDTO));
        } else {
            response = ResponseEntity.badRequest().build();
        }
        return response;
    }

    @GetMapping
    public ResponseEntity<List<BookingDTO>> findAll() {
        return ResponseEntity.ok(bookingService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingDTO> findById(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<BookingDTO> booking = bookingService.findById(id);

        if (booking.isPresent()) {
            return ResponseEntity.ok(booking.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) throws ResourceNotFoundException {
        try {
            bookingService.delete(id);
            return ResponseEntity.ok("Reserva eliminada con ID: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Reserva no encontrada con ID: " + id);
        }
    }
}
