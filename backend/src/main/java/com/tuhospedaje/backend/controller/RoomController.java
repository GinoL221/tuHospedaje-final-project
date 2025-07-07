package com.tuhospedaje.backend.controller;

import com.tuhospedaje.backend.dto.RoomDTO;
import com.tuhospedaje.backend.service.IRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/habitaciones")
public class RoomController {
    private final IRoomService roomService;

    @Autowired
    public RoomController(IRoomService roomService) {
        this.roomService = roomService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<RoomDTO> save(@RequestBody RoomDTO roomDTO) {
        if (roomDTO.getId() != null) {
            return ResponseEntity.badRequest().build();
        }

        RoomDTO saved = roomService.save(roomDTO);
        return ResponseEntity.status(201).body(saved);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public void update(@RequestBody RoomDTO dto) {
        if (roomService.findById(dto.getId()).isPresent()) {
            roomService.update(dto);
        } else {
            throw new IllegalArgumentException("La habitación con ID " + dto.getId() + " no existe.");
        }
    }

    @GetMapping
    public ResponseEntity<List<RoomDTO>> findAll() {
        return ResponseEntity.ok(roomService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomDTO> findById(@PathVariable Long id) {
        Optional<RoomDTO> room = roomService.findById(id);

        if (room.isPresent()) {
            return ResponseEntity.ok(room.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        if (roomService.findById(id).isPresent()) {
            roomService.delete(id);
            return ResponseEntity.ok("Habitación eliminada con ID: " + id);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Habitación con ID " + id + " no encontrada.");
        }
    }
}
