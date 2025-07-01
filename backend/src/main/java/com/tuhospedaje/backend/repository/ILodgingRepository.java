package com.tuhospedaje.backend.repository;

import com.tuhospedaje.backend.entity.Lodging;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ILodgingRepository extends JpaRepository<Lodging, Long> {
    Optional<Lodging> findByName(String name);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);
    
    List<Lodging> findByNameContainingIgnoreCase(String name);
}
