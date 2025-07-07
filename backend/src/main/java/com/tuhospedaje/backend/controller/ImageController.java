package com.tuhospedaje.backend.controller;

import com.tuhospedaje.backend.dto.ImageDTO;
import com.tuhospedaje.backend.service.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/imagenes")
public class ImageController {
    private final IImageService imageService;

    @Autowired
    public ImageController(IImageService imageService) {
        this.imageService = imageService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{lodgingId}")
    public ResponseEntity<ImageDTO> save(@RequestBody ImageDTO imageDTO, @PathVariable Long lodgingId) {
        try {
            ImageDTO saved = imageService.save(imageDTO, lodgingId);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public ResponseEntity<ImageDTO> update(@RequestBody ImageDTO imageDTO) {
        try {
            ImageDTO updated = imageService.update(imageDTO);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            imageService.delete(id);
            return ResponseEntity.ok("Imagen eliminada con ID: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Imagen no encontrada con ID: " + id);
        }
    }

    @GetMapping
    public ResponseEntity<List<ImageDTO>> findAll() {
        return ResponseEntity.ok(imageService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImageDTO> findById(@PathVariable Long id) {
        Optional<ImageDTO> image = imageService.findById(id);
        return image.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/alojamiento/{lodgingId}")
    public ResponseEntity<List<ImageDTO>> findByLodgingId(@PathVariable Long lodgingId) {
        try {
            List<ImageDTO> images = imageService.findByLodgingId(lodgingId);
            return ResponseEntity.ok(images);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
