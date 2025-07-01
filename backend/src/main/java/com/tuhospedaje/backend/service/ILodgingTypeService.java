package com.tuhospedaje.backend.service;

import com.tuhospedaje.backend.dto.LodgingTypeDTO;
import com.tuhospedaje.backend.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface ILodgingTypeService {
    LodgingTypeDTO save(LodgingTypeDTO dto);

    LodgingTypeDTO update(LodgingTypeDTO dto) throws ResourceNotFoundException;

    void delete(Long id) throws ResourceNotFoundException;

    List<LodgingTypeDTO> findAll();

    Optional<LodgingTypeDTO> findById(Long id);
}
