package com.archivos.ecommerce.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    private String userId;

    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank
    @Email
    @Column(name = "email_address", unique = true, nullable = false)
    private String emailAddress;

    @NotBlank
    @Column(nullable = false)
    private String password;

    @NotBlank
    @Column(name = "address")
    private String address;

    @NotNull
    @Column(name = "dpi",  nullable = false, unique = true)
    private Long dpi;

    @Column(name = "user_status", nullable = false)
    private String userStatus;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    public User(String emailAddress, String password, Role role) {
        this.emailAddress = emailAddress;
        this.password = password;
        this.role = role;
    }
}
