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
    private final ILodgingRepository hotelRepository;

    @Autowired
    public LodgingServiceImpl(ILodgingTypeRepository propertyTypeRepository, ILodgingRepository hotelRepository) {
        this.propertyTypeRepository = propertyTypeRepository;
        this.hotelRepository = hotelRepository;
    }

    @Override
    public LodgingDTO save(LodgingDTO dto) {
        LodgingType type = propertyTypeRepository.findById(dto.getPropertyTypeId())
                .orElseThrow(() -> new RuntimeException("Tipo de alojamiento no encontrado con ID: " + dto.getPropertyTypeId()));

        Lodging lodging = getHotel(dto, type);
        Lodging saved = hotelRepository.save(lodging);
        return mapToDTO(saved);
    }

    @Override
    public LodgingDTO update(LodgingDTO dto) {
        Lodging lodging = hotelRepository.findById(dto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Hotel no encontrado con ID: " + dto.getId()));

        LodgingType type = propertyTypeRepository.findById(dto.getPropertyTypeId())
                .orElseThrow(() -> new RuntimeException("Tipo de alojamiento no encontrado con ID: " + dto.getPropertyTypeId()));

        lodging.setName(dto.getName());
        lodging.setDescription(dto.getDescription());
        lodging.setRating(dto.getRating());
        lodging.setAddress(dto.getAddress());
        lodging.setCity(dto.getCity());
        lodging.setCountry(dto.getCountry());
        lodging.setPhoneNumber(dto.getPhoneNumber());
        lodging.setEmail(dto.getEmail());
        lodging.setImage(dto.getImage());
        lodging.setLodgingType(type);

        Lodging updated = hotelRepository.save(lodging);
        return mapToDTO(updated);
    }

    @Override
    public void delete(Long id) {
        if (!hotelRepository.existsById(id)) {
            throw new ResourceNotFoundException("Hotel no encontrado con ID: " + id);
        }
        hotelRepository.deleteById(id);
    }

    @Override
    public List<LodgingDTO> findAll() {
        return hotelRepository.findAll().stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public Optional<LodgingDTO> findById(Long id) {
        return hotelRepository.findById(id).map(this::mapToDTO);
    }

    @Override
    public List<LodgingDTO> findByName(String name) {
        return hotelRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    private LodgingDTO mapToDTO(Lodging lodging) {
        return LodgingDTO.builder()
                .id(lodging.getId())
                .name(lodging.getName())
                .description(lodging.getDescription())
                .rating(lodging.getRating())
                .address(lodging.getAddress())
                .city(lodging.getCity())
                .country(lodging.getCountry())
                .phoneNumber(lodging.getPhoneNumber())
                .email(lodging.getEmail())
                .image(lodging.getImage())
                .propertyTypeId(lodging.getLodgingType().getId())
                .build();
    }

    private Lodging getHotel(LodgingDTO dto, LodgingType type) {
        Lodging lodging = new Lodging();
        lodging.setName(dto.getName());
        lodging.setDescription(dto.getDescription());
        lodging.setRating(dto.getRating());
        lodging.setAddress(dto.getAddress());
        lodging.setCity(dto.getCity());
        lodging.setCountry(dto.getCountry());
        lodging.setPhoneNumber(dto.getPhoneNumber());
        lodging.setEmail(dto.getEmail());
        lodging.setImage(dto.getImage());
        lodging.setLodgingType(type);
        return lodging;
    }
}
