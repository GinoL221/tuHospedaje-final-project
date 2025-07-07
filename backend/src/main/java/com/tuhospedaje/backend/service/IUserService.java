package com.tuhospedaje.backend.service;

import com.tuhospedaje.backend.dto.UserDTO;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<UserDTO> findAll();

    Optional<UserDTO> findById(Long id);

    Optional<UserDTO> findByEmail(String email);

    Optional<UserDTO> delete(Long id);

    UserDTO update(UserDTO userDTO);
}
