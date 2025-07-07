package com.tuhospedaje.backend.service;

import com.tuhospedaje.backend.dto.ImageDTO;
import com.tuhospedaje.backend.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface IImageService {

    ImageDTO save(ImageDTO imageDTO, Long lodgingId) throws ResourceNotFoundException;

    ImageDTO update(ImageDTO imageDTO) throws ResourceNotFoundException;

    Optional<ImageDTO> delete(Long id) throws ResourceNotFoundException;

    // Devuelve una lista de todas las imágenes
    List<ImageDTO> findAll();

    Optional<ImageDTO> findById(Long id);

    // Devuelve una lista de imágenes asociadas a un alojamiento específico
    List<ImageDTO> findByLodgingId(Long lodgingId) throws ResourceNotFoundException;
}
