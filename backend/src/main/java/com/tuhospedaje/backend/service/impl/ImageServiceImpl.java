package com.tuhospedaje.backend.service.impl;

import com.tuhospedaje.backend.dto.ImageDTO;
import com.tuhospedaje.backend.entity.Image;
import com.tuhospedaje.backend.entity.Lodging;
import com.tuhospedaje.backend.exception.ResourceNotFoundException;
import com.tuhospedaje.backend.repository.IImageRepository;
import com.tuhospedaje.backend.repository.ILodgingRepository;
import com.tuhospedaje.backend.service.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImageServiceImpl implements IImageService {
    private final IImageRepository imageRepository;
    private final ILodgingRepository lodgingRepository;

    @Autowired
    public ImageServiceImpl(IImageRepository imageRepository, ILodgingRepository lodgingRepository) {
        this.imageRepository = imageRepository;
        this.lodgingRepository = lodgingRepository;
    }

    @Override
    public ImageDTO save(ImageDTO dto, Long lodgingId) {
        if (dto == null) {
            throw new IllegalArgumentException("El objeto de imagen no puede ser nulo.");
        }

        Lodging lodging = lodgingRepository.findById(lodgingId)
                .orElseThrow(() -> new ResourceNotFoundException("Alojamiento no encontrado con ID: " + lodgingId));

        if (dto.getUrl() == null || dto.getUrl().trim().isEmpty()) {
            throw new IllegalArgumentException("La URL de la imagen es obligatoria.");
        }

        Image image = new Image();
        image.setUrl(dto.getUrl());
        image.setFeatured(dto.isFeatured());
        image.setPosition(dto.getPosition());
        image.setLodging(lodging);

        Image saved = imageRepository.save(image);
        return mapToDTO(saved);
    }

    @Override
    public ImageDTO update(ImageDTO dto) {
        if (dto == null || dto.getId() == null) {
            throw new IllegalArgumentException("Debe proporcionar un ID de imagen para actualizar.");
        }

        Image image = imageRepository.findById(dto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Imagen no encontrada con ID: " + dto.getId()));

        if (dto.getUrl() == null || dto.getUrl().trim().isEmpty()) {
            throw new IllegalArgumentException("La URL de la imagen es obligatoria.");
        }

        image.setUrl(dto.getUrl());
        image.setFeatured(dto.isFeatured());
        image.setPosition(dto.getPosition());

        Image updated = imageRepository.save(image);
        return mapToDTO(updated);
    }

    @Override
    public Optional<ImageDTO> delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Debe proporcionar un ID para eliminar la imagen.");
        }

        Image image = imageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Imagen no encontrada con ID: " + id));

        imageRepository.delete(image);
        return Optional.of(mapToDTO(image));
    }

    @Override
    public List<ImageDTO> findAll() {
        return imageRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public Optional<ImageDTO> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Debe proporcionar un ID para buscar la imagen.");
        }

        return imageRepository.findById(id).map(this::mapToDTO);
    }

    @Override
    public List<ImageDTO> findByLodgingId(Long lodgingId) {
        if (lodgingId == null) {
            throw new IllegalArgumentException("Debe proporcionar un ID de alojamiento.");
        }

        Lodging lodging = lodgingRepository.findById(lodgingId)
                .orElseThrow(() -> new ResourceNotFoundException("Alojamiento no encontrado con ID: " + lodgingId));

        return lodging.getImages().stream()
                .map(this::mapToDTO)
                .toList();
    }

    private ImageDTO mapToDTO(Image image) {
        return ImageDTO.builder()
                .id(image.getId())
                .url(image.getUrl())
                .featured(image.isFeatured())
                .position(image.getPosition())
                .build();
    }
}
