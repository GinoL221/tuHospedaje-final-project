package com.tuhospedaje.backend.authentication;

import com.tuhospedaje.backend.configuration.JwtService;
import com.tuhospedaje.backend.entity.User;
import com.tuhospedaje.backend.enums.RoleEnum;
import com.tuhospedaje.backend.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    // Registra un nuevo usuario en el sistema
    public AuthenticationResponse register(RegisterRequest request) {
        // Crea un nuevo usuario con los datos proporcionados en la solicitud
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword())) // Codifica la contraseña
                .roleEnum(RoleEnum.USER) // Asigna el rol de usuario
                .build();

        // Guarda el usuario en la base de datos
        userRepository.save(user);

        // Genera un token JWT para el usuario registrado
        var jwt = jwtService.generateToken(user);

        // Devuelve la respuesta con el token generado
        return AuthenticationResponse.builder()
                .token(jwt)
                .build();
    }

    // Autentica a un usuario existente
    public AuthenticationResponse login(AuthenticationRequest request) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = (User) auth.getPrincipal();

        var jwt = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwt)
                .build();
    }

    public AuthenticationResponse registerAdmin(RegisterRequest request) {
        var admin = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roleEnum(RoleEnum.ADMIN)
                .build();

        userRepository.save(admin);

        var jwt = jwtService.generateToken(admin);

        return AuthenticationResponse.builder()
                .token(jwt)
                .build();
    }
}
