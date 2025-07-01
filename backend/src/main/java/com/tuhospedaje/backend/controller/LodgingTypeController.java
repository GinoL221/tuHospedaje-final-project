package com.tuhospedaje.backend.controller;

import com.tuhospedaje.backend.dto.LodgingTypeDTO;
import com.tuhospedaje.backend.service.ILodgingTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tipos-de-alojamientos")
public class LodgingTypeController {
    private final ILodgingTypeService lodgingTypeService;

    @Autowired
    public LodgingTypeController(ILodgingTypeService lodgingTypeService) {
        this.lodgingTypeService = lodgingTypeService;
    }

    @PostMapping
    public ResponseEntity<LodgingTypeDTO> save(@RequestBody LodgingTypeDTO dto) {
        LodgingTypeDTO saved = lodgingTypeService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LodgingTypeDTO> update(@PathVariable Long id, @RequestBody LodgingTypeDTO dto) {
        dto.setId(id);
        LodgingTypeDTO updated = lodgingTypeService.update(dto);
        return ResponseEntity.ok(updated);
    }

    @GetMapping
    public List<LodgingTypeDTO> findAll() {
        return lodgingTypeService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LodgingTypeDTO> findById(@PathVariable Long id) {
        return lodgingTypeService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        lodgingTypeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

