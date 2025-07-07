package com.tuhospedaje.backend.dto;

import com.tuhospedaje.backend.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private RoleEnum role;
    private String image;

    public UserDTO() {
    }
}

