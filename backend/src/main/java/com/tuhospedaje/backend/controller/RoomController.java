package com.tuhospedaje.backend.controller;

import com.tuhospedaje.backend.dto.RoomDTO;
import com.tuhospedaje.backend.service.IRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<RoomDTO> save(@RequestBody RoomDTO dto) {
        RoomDTO createdRoom = roomService.save(dto);
        return ResponseEntity.ok(createdRoom);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public void update(@RequestBody RoomDTO dto) {
        roomService.update(dto);
    }

    @GetMapping
    public ResponseEntity<List<RoomDTO>> findAll() {
        return ResponseEntity.ok(roomService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomDTO> findById(@PathVariable Long id) {
        return roomService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        roomService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
