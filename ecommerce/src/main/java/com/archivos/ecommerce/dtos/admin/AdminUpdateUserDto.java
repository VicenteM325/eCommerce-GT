package com.archivos.ecommerce.dtos.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AdminUpdateUserDto {

    @NotBlank
    private String name;

    @NotBlank
    private String emailAddress;

    private String password;
    private String address;

    @NotNull
    private Long dpi;

    @NotBlank
    private String role;

    @NotBlank
    private String userStatus;
}
