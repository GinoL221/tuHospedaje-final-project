package com.tuhospedaje.backend.service.impl;

import com.tuhospedaje.backend.dto.LodgingTypeDTO;
import com.tuhospedaje.backend.entity.LodgingType;
import com.tuhospedaje.backend.exception.ResourceNotFoundException;
import com.tuhospedaje.backend.repository.ILodgingTypeRepository;
import com.tuhospedaje.backend.service.ILodgingTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LodgingTypeServiceImpl implements ILodgingTypeService {
    private final ILodgingTypeRepository lodgingTypeRepository;

    @Override
    public LodgingTypeDTO save(LodgingTypeDTO dto) {
        if (dto == null || dto.getName() == null || dto.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del tipo de alojamiento es obligatorio.");
        }

        if (lodgingTypeRepository.existsByNameIgnoreCase(dto.getName().trim())) {
            throw new IllegalArgumentException("Ya existe un tipo de alojamiento con el nombre: " + dto.getName());
        }

        LodgingType entity = new LodgingType();
        entity.setName(dto.getName().trim());

        LodgingType saved = lodgingTypeRepository.save(entity);
        return mapToDTO(saved);
    }

    @Override
    public LodgingTypeDTO update(LodgingTypeDTO dto) throws ResourceNotFoundException {
        if (dto == null || dto.getId() == null) {
            throw new IllegalArgumentException("Debe proporcionar un ID válido para actualizar el tipo de alojamiento.");
        }

        LodgingType entity = lodgingTypeRepository.findById(dto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de alojamiento no encontrado con ID: " + dto.getId()));

        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del tipo de alojamiento es obligatorio.");
        }

        entity.setName(dto.getName().trim());

        LodgingType updated = lodgingTypeRepository.save(entity);
        return mapToDTO(updated);
    }

    @Override
    public Optional<LodgingTypeDTO> delete(Long id) throws ResourceNotFoundException {
        if (id == null) {
            throw new IllegalArgumentException("Debe proporcionar un ID para eliminar el tipo de alojamiento.");
        }

        LodgingType lodgingType = lodgingTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de alojamiento no encontrado con ID: " + id));

        lodgingTypeRepository.deleteById(id);

        LodgingTypeDTO dto = mapToDTO(lodgingType);
        return Optional.of(dto);
    }

    @Override
    public List<LodgingTypeDTO> findAll() {
        return lodgingTypeRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<LodgingTypeDTO> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Debe proporcionar un ID para buscar el tipo de alojamiento.");
        }

        return lodgingTypeRepository.findById(id).map(this::mapToDTO);
    }

    private LodgingTypeDTO mapToDTO(LodgingType entity) {
        return LodgingTypeDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}
