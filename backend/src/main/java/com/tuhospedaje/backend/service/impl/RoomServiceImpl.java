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
                .orElseThrow(() -> new ResourceNotFoundException("Establecimiento no encontrado con ID: " + dto.getLodgingId()));

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
    public List<RoomDTO> findAll() {
        return roomRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<RoomDTO> findById(Long id) {
        return roomRepository.findById(id)
                .map(this::mapToDTO);
    }

    @Override
    public Optional<RoomDTO> delete(Long id) {
        Optional<Room> roomToDelete = roomRepository.findById(id);
        if (roomToDelete.isPresent()) {
            RoomDTO dto = mapToDTO(roomToDelete.get());
            roomRepository.deleteById(id);
            return Optional.of(dto);
        } else {
            throw new ResourceNotFoundException("Habitación no encontrada con ID: " + id);
        }
    }

    @Override
    public RoomDTO update(RoomDTO dto) {
        Room existing = roomRepository.findById(dto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Habitación no encontrada con ID: " + dto.getId()));

        var lodging = lodgingRepository.findById(dto.getLodgingId())
                .orElseThrow(() -> new ResourceNotFoundException("Establecimiento no encontrado con ID: " + dto.getLodgingId()));

        existing.setLodging(lodging);
        existing.setRoomType(dto.getRoomType());
        existing.setRoomNumber(dto.getRoomNumber());
        existing.setAvailable(dto.getAvailable());
        existing.setPrice(dto.getPrice());

        Room updated = roomRepository.save(existing);
        return mapToDTO(updated);
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
