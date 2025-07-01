package com.tuhospedaje.backend.service.impl;

import com.tuhospedaje.backend.dto.RoomDTO;
import com.tuhospedaje.backend.entity.Room;
import com.tuhospedaje.backend.exception.ResourceNotFoundException;
import com.tuhospedaje.backend.repository.ILodgingRepository;
import com.tuhospedaje.backend.repository.IRoomRepository;
import com.tuhospedaje.backend.service.IRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements IRoomService {
    private final IRoomRepository roomRepository;
    private final ILodgingRepository lodgingRepository;

    @Override
    public RoomDTO save(RoomDTO dto) {
        var lodging = lodgingRepository.findById(dto.getLodgingId())
                .orElseThrow(() -> new ResourceNotFoundException("Establecimiento no encontrado"));

        Room room = new Room();
        room.setLodging(lodging);
        room.setRoomType(dto.getRoomType());
        room.setRoomNumber(dto.getRoomNumber());
        room.setAvailable(dto.getAvailable());
        room.setPrice(dto.getPrice());

        Room saved = roomRepository.save(room);
        return mapToDTO(saved);
    }

    @Override
    public List<RoomDTO> getAll() {
        return roomRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<RoomDTO> getById(Long id) {
        return roomRepository.findById(id)
                .map(this::mapToDTO);
    }

    @Override
    public void delete(Long id) {
        if (!roomRepository.existsById(id)) {
            throw new ResourceNotFoundException("Habitación no encontrada con ID: " + id);
        }
        roomRepository.deleteById(id);
    }

    @Override
    public void update(RoomDTO dto) {
        Room room = roomRepository.findById(dto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Habitación no encontrada con ID: " + dto.getId()));

        var lodging = lodgingRepository.findById(dto.getLodgingId())
                .orElseThrow(() -> new ResourceNotFoundException("Establecimiento no encontrado"));

        room.setLodging(lodging);
        room.setRoomType(dto.getRoomType());
        room.setRoomNumber(dto.getRoomNumber());
        room.setAvailable(dto.getAvailable());
        room.setPrice(dto.getPrice());

        roomRepository.save(room);
    }

    private RoomDTO mapToDTO(Room room) {
        return RoomDTO.builder()
                .id(room.getId())
                .lodgingId(room.getLodging().getId())
                .roomType(room.getRoomType())
                .roomNumber(room.getRoomNumber())
                .available(room.getAvailable())
                .price(room.getPrice())
                .build();
    }
}
