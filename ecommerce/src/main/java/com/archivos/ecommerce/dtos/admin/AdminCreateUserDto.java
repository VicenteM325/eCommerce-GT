package com.archivos.ecommerce.dtos.admin;

import com.archivos.ecommerce.enums.RoleList;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AdminCreateUserDto {

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String emailAddress;

    @NotBlank
    private String password;

    @NotBlank
    private String address;

    @NotNull
    private Long dpi;

    @NotBlank
    private String userStatus;

    @NotNull
    private RoleList role;
}
