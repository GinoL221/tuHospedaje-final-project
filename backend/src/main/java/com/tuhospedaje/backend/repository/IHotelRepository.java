package com.tuhospedaje.backend.repository;

import com.tuhospedaje.backend.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IHotelRepository extends JpaRepository<Hotel, Long> {
    Optional<Hotel> findByName(String name);
}
