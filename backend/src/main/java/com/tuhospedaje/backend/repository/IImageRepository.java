package com.tuhospedaje.backend.repository;

import com.tuhospedaje.backend.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IImageRepository extends JpaRepository<Image, Long> {
}
