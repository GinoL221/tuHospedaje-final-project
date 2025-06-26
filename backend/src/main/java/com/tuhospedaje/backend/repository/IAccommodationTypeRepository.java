package com.tuhospedaje.backend.repository;

import com.tuhospedaje.backend.entity.AccommodationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAccommodationTypeRepository extends JpaRepository<AccommodationType, Long> {
}
