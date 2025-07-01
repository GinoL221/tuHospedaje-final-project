package com.tuhospedaje.backend.controller;

import com.tuhospedaje.backend.dto.BookingDTO;
import com.tuhospedaje.backend.exception.ResourceNotFoundException;
import com.tuhospedaje.backend.service.IBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/turnos")
public class BookingController {
    private final IBookingService bookingService;

    @Autowired
    public BookingController(IBookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<BookingDTO> save(@RequestBody BookingDTO bookingDTO) {
        BookingDTO savedBooking = bookingService.save(bookingDTO);
        return ResponseEntity.ok(savedBooking);
    }

    @PutMapping
    public ResponseEntity<BookingDTO> update(@RequestBody BookingDTO bookingDTO) {
        if (bookingService.findById(bookingDTO.getId()).isPresent()) {
            return ResponseEntity.ok(bookingService.update(bookingDTO));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<BookingDTO>> findAll() {
        return ResponseEntity.ok(bookingService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingDTO> findById(@PathVariable Long id) throws ResourceNotFoundException {
        return bookingService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
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
