package com.tuhospedaje.backend.service.impl;

import com.tuhospedaje.backend.dto.LodgingDTO;
import com.tuhospedaje.backend.entity.Lodging;
import com.tuhospedaje.backend.entity.LodgingType;
import com.tuhospedaje.backend.exception.ResourceNotFoundException;
import com.tuhospedaje.backend.repository.ILodgingRepository;
import com.tuhospedaje.backend.repository.ILodgingTypeRepository;
import com.tuhospedaje.backend.service.ILodgingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LodgingServiceImpl implements ILodgingService {
    private final ILodgingTypeRepository propertyTypeRepository;
    private final ILodgingRepository lodgingRepository;

    @Autowired
    public LodgingServiceImpl(ILodgingTypeRepository propertyTypeRepository, ILodgingRepository lodgingRepository) {
        this.propertyTypeRepository = propertyTypeRepository;
        this.lodgingRepository = lodgingRepository;
    }

    @Override
    public LodgingDTO save(LodgingDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("El objeto LodgingDTO no puede ser nulo.");
        }

        if (dto.getEmail() == null || dto.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("El email del alojamiento es obligatorio.");
        }

        if (lodgingRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Ya existe un alojamiento con el email: " + dto.getEmail());
        }

        LodgingType type = propertyTypeRepository.findById(dto.getPropertyTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de alojamiento no encontrado con ID: " + dto.getPropertyTypeId()));

        Lodging lodging = dto.toEntity();
        lodging.setLodgingType(type);

        Lodging saved = lodgingRepository.save(lodging);
        return LodgingDTO.fromEntity(saved);
    }

    @Override
    public LodgingDTO update(LodgingDTO dto) {
        if (dto == null || dto.getId() == null) {
            throw new IllegalArgumentException("Debe proporcionar un ID válido para actualizar el alojamiento.");
        }

        Lodging lodging = lodgingRepository.findById(dto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Alojamiento no encontrado con ID: " + dto.getId()));

        LodgingType type = propertyTypeRepository.findById(dto.getPropertyTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de alojamiento no encontrado con ID: " + dto.getPropertyTypeId()));

        lodging.setName(dto.getName());
        lodging.setDescription(dto.getDescription());
        lodging.setRating(dto.getRating());
        lodging.setAddress(dto.getAddress());
        lodging.setCity(dto.getCity());
        lodging.setCountry(dto.getCountry());
        lodging.setPhoneNumber(dto.getPhoneNumber());
        lodging.setEmail(dto.getEmail());
        lodging.setLodgingType(type);

        Lodging updated = lodgingRepository.save(lodging);
        return LodgingDTO.fromEntity(updated);
    }

    @Override
    public Optional<LodgingDTO> delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Debe proporcionar un ID válido para eliminar el alojamiento.");
        }

        Lodging lodging = lodgingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alojamiento no encontrado con ID: " + id));

        lodgingRepository.deleteById(id);
        return Optional.of(LodgingDTO.fromEntity(lodging));
    }

    @Override
    public List<LodgingDTO> findAll() {
        return lodgingRepository.findAll()
                .stream()
                .map(LodgingDTO::fromEntity)
                .toList();
    }

    @Override
    public Optional<LodgingDTO> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Debe proporcionar un ID para buscar el alojamiento.");
        }

        return lodgingRepository.findById(id).map(LodgingDTO::fromEntity);
    }

    @Override
    public List<LodgingDTO> findByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Debe proporcionar un nombre para buscar alojamientos.");
        }

        return lodgingRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(LodgingDTO::fromEntity)
                .toList();
    }
}
