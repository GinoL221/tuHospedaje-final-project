package com.tuhospedaje.backend.data;

import com.tuhospedaje.backend.entity.LodgingType;
import com.tuhospedaje.backend.entity.User;
import com.tuhospedaje.backend.enums.RoleEnum;
import com.tuhospedaje.backend.exception.ResourceNotFoundException;
import com.tuhospedaje.backend.repository.ILodgingTypeRepository;
import com.tuhospedaje.backend.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final IUserRepository userRepository;
    private final ILodgingTypeRepository lodgingTypeRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws ResourceNotFoundException {
        createDefaultUsers();
        createDefaultLodgingTypes();
    }

    private void createDefaultUsers() {
        if (userRepository.findByEmail("admin@tuhospedaje.com").isEmpty()) {
            User admin = User.builder()
                    .firstName("Admin")
                    .lastName("User")
                    .email("admin@tuhospedaje.com")
                    .password(passwordEncoder.encode("admin123"))
                    .roleEnum(RoleEnum.ADMIN)
                    .image("/images/avatar_admin.jpg")
                    .build();
            userRepository.save(admin);
        }

        if (userRepository.findByEmail("user@tuhospedaje.com").isEmpty()) {
            User user = User.builder()
                    .firstName("Usuario")
                    .lastName("Común")
                    .email("user@tuhospedaje.com")
                    .password(passwordEncoder.encode("user123"))
                    .roleEnum(RoleEnum.USER)
                    .image("/images/avatar_user.jpg")
                    .build();
            userRepository.save(user);
        }
    }

    private void createDefaultLodgingTypes() {
        if (lodgingTypeRepository.count() == 0) {
            List<LodgingType> types = List.of(
                    new LodgingType("Hotel"),
                    new LodgingType("Cabaña"),
                    new LodgingType("Departamento"),
                    new LodgingType("Hostal")
            );
            lodgingTypeRepository.saveAll(types);
        }
    }
}
