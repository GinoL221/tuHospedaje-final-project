package com.tuhospedaje.backend.service;

import com.tuhospedaje.backend.dto.RoomDTO;

import java.util.List;
import java.util.Optional;

public interface IRoomService {
    RoomDTO save(RoomDTO roomDTO);

    List<RoomDTO> findAll();

    Optional<RoomDTO> findById(Long id);

    Optional<RoomDTO> delete(Long id);

    RoomDTO update(RoomDTO dto);
}
