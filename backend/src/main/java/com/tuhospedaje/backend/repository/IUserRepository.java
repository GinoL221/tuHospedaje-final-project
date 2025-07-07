package com.tuhospedaje.backend.repository;

import com.tuhospedaje.backend.entity.User;
import com.tuhospedaje.backend.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    boolean existsByRoleEnum(RoleEnum roleEnum);
}
