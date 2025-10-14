package com.archivos.ecommerce.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class NewUserDto {

    private String name;
    private String emailAddress;
    private String password;
    private String address;
    private Long dpi;
    private String userStatus;
}

