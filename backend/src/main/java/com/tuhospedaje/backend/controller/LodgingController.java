package com.tuhospedaje.backend.controller;

import com.tuhospedaje.backend.dto.LodgingDTO;
import com.tuhospedaje.backend.service.ILodgingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alojamientos")
public class LodgingController {
    private final ILodgingService lodgingService;

    @Autowired
    public LodgingController(ILodgingService lodgingService) {
        this.lodgingService = lodgingService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<LodgingDTO> save(@RequestBody LodgingDTO lodgingDTO) {
        return ResponseEntity.ok(lodgingService.save(lodgingDTO));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public ResponseEntity<Void> update(@RequestBody LodgingDTO lodgingDTO) {
        if (lodgingDTO.getId() == null) {
            return ResponseEntity.badRequest().build();
        }
        lodgingService.update(lodgingDTO);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<LodgingDTO>> findAll() {
        return ResponseEntity.ok(lodgingService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LodgingDTO> findById(@PathVariable Long id) {
        return lodgingService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<LodgingDTO>> findByName(@RequestParam String query) {
        return ResponseEntity.ok(lodgingService.findByName(query));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        lodgingService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
