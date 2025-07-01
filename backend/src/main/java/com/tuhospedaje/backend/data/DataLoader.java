package com.tuhospedaje.backend.data;

import com.tuhospedaje.backend.entity.LodgingType;
import com.tuhospedaje.backend.repository.ILodgingTypeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final ILodgingTypeRepository propertyTypeRepository;

    public DataLoader(ILodgingTypeRepository propertyTypeRepository) {
        this.propertyTypeRepository = propertyTypeRepository;
    }

    @Override
    public void run(String... args) {
        if (propertyTypeRepository.count() == 0) {
            propertyTypeRepository.save(new LodgingType("Hotel"));
            propertyTypeRepository.save(new LodgingType("Hostel"));
            propertyTypeRepository.save(new LodgingType("Departamento"));
            propertyTypeRepository.save(new LodgingType("Cabaña"));
        }
    }
}