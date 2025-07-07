package com.tuhospedaje.backend.controller;

import com.tuhospedaje.backend.dto.LodgingTypeDTO;
import com.tuhospedaje.backend.service.ILodgingTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tipos-de-alojamientos")
public class LodgingTypeController {
    private final ILodgingTypeService lodgingTypeService;

    @Autowired
    public LodgingTypeController(ILodgingTypeService lodgingTypeService) {
        this.lodgingTypeService = lodgingTypeService;
    }

    @PostMapping
    public ResponseEntity<LodgingTypeDTO> save(@RequestBody LodgingTypeDTO lodgingTypeDTO) {
        if (lodgingTypeDTO.getId() != null) {
            return ResponseEntity.badRequest().build();
        }

        LodgingTypeDTO saved = lodgingTypeService.save(lodgingTypeDTO);
        return ResponseEntity.status(201).body(saved);
    }

    @PutMapping
    public ResponseEntity<LodgingTypeDTO> update(@RequestBody LodgingTypeDTO lodgingTypeDTO) {
        ResponseEntity<LodgingTypeDTO> response;

        if (lodgingTypeService.findById(lodgingTypeDTO.getId()).isPresent()) {
            response = ResponseEntity.ok(lodgingTypeService.update(lodgingTypeDTO));
        } else {
            response = ResponseEntity.badRequest().build();
        }
        return response;
    }

    @GetMapping
    public ResponseEntity<List<LodgingTypeDTO>> findAll() {
        return ResponseEntity.ok(lodgingTypeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LodgingTypeDTO> findById(@PathVariable Long id) {
        Optional<LodgingTypeDTO> lodgingType = lodgingTypeService.findById(id);

        if (lodgingType.isPresent()) {
            return ResponseEntity.ok(lodgingType.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        lodgingTypeService.delete(id);
        return ResponseEntity.ok("Tipo de alojamiento eliminado con ID: " + id);
    }
}

