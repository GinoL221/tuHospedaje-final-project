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
        LodgingType entity = new LodgingType();
        entity.setName(dto.getName());
        LodgingType saved = lodgingTypeRepository.save(entity);
        return mapToDTO(saved);
    }

    @Override
    public LodgingTypeDTO update(LodgingTypeDTO dto) throws ResourceNotFoundException {
        LodgingType entity = lodgingTypeRepository.findById(dto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de alojamiento no encontrado con ID: " + dto.getId()));
        entity.setName(dto.getName());
        LodgingType updated = lodgingTypeRepository.save(entity);
        return mapToDTO(updated);
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        if (!lodgingTypeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Tipo de alojamiento no encontrado con ID: " + id);
        }
        lodgingTypeRepository.deleteById(id);
    }

    @Override
    public List<LodgingTypeDTO> findAll() {
        return lodgingTypeRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<LodgingTypeDTO> findById(Long id) {
        return lodgingTypeRepository.findById(id).map(this::mapToDTO);
    }

    private LodgingTypeDTO mapToDTO(LodgingType entity) {
        return LodgingTypeDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}

