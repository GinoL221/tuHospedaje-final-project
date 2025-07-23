package com.tuhospedaje.backend.authentication;

import com.tuhospedaje.backend.configuration.JwtService;
import com.tuhospedaje.backend.entity.User;
import com.tuhospedaje.backend.enums.RoleEnum;
import com.tuhospedaje.backend.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    // Registra un nuevo usuario en el sistema
    public AuthenticationResponse register(RegisterRequest request) {
        RoleEnum roleToAssign = RoleEnum.USER;

        if (request.getRole() != null && request.getRole().equalsIgnoreCase("ADMIN")) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null ||
                    authentication.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                throw new AccessDeniedException("No estás autorizado para crear un usuario administrador.");
            }

            roleToAssign = RoleEnum.ADMIN;
        }

        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roleEnum(roleToAssign)
                .build();

        // Asignar imagen por defecto si no viene
        if (user.getImage() == null || user.getImage().isBlank()) {
            user.setImage("https://ui-avatars.com/api/?name="
                    + user.getFirstName() + "+" + user.getLastName());
        }

        userRepository.save(user);

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("firstName", user.getFirstName());
        extraClaims.put("lastName", user.getLastName());
        extraClaims.put("role", user.getRoleEnum().name());
        extraClaims.put("image", user.getImage());

        String jwt = jwtService.generateToken(extraClaims, user);

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

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("firstName", user.getFirstName());
        extraClaims.put("lastName", user.getLastName());
        extraClaims.put("role", user.getRoleEnum().name());
        extraClaims.put("image", user.getImage());

        var jwt = jwtService.generateToken(extraClaims, user);

        return AuthenticationResponse.builder()
                .token(jwt)
                .build();
    }

}
