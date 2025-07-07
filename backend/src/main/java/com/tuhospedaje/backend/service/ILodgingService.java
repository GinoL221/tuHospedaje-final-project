package com.tuhospedaje.backend.service;

import com.tuhospedaje.backend.dto.LodgingDTO;
import com.tuhospedaje.backend.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface ILodgingService {
    LodgingDTO save(LodgingDTO lodgingDTO);

    LodgingDTO update(LodgingDTO lodgingDTO) throws ResourceNotFoundException;

    Optional<LodgingDTO> delete(Long id) throws ResourceNotFoundException;

    List<LodgingDTO> findAll();

    Optional<LodgingDTO> findById(Long id);

    List<LodgingDTO> findByName(String name);
}

