package com.tuhospedaje.backend.service;

import com.tuhospedaje.backend.dto.RoomDTO;

import java.util.List;
import java.util.Optional;

public interface IRoomService {
    RoomDTO save(RoomDTO roomDTO);

    List<RoomDTO> getAll();

    Optional<RoomDTO> getById(Long id);

    void delete(Long id);

    void update(RoomDTO dto);
}
