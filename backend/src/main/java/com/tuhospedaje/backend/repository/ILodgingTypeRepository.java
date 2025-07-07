package com.tuhospedaje.backend.repository;

import com.tuhospedaje.backend.entity.LodgingType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ILodgingTypeRepository extends JpaRepository<LodgingType, Long> {
    Optional<LodgingType> findByName(String name);

    boolean existsByNameIgnoreCase(String trim);
}
