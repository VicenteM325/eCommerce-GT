package com.archivos.ecommerce.controllers;

import com.archivos.ecommerce.dtos.admin.AdminCreateUserDto;
import com.archivos.ecommerce.dtos.ApiMessage;
import com.archivos.ecommerce.dtos.admin.AdminUpdateUserDto;
import com.archivos.ecommerce.entities.User;
import com.archivos.ecommerce.repositories.RoleRepository;
import com.archivos.ecommerce.services.AuthService;
import com.archivos.ecommerce.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/users")
public class AdminUserController {

    private final AuthService authService;
    private final UserService userService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminUserController(AuthService authService, UserService userService,
                               RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.authService = authService;
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //Solo el admin puede crear nuevos usuarios con roles ADMIN, LOGISTICS Y MODERADOR
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiMessage> createUser(@Valid @RequestBody AdminCreateUserDto dto) {
        try {
            authService.registerUserByAdmin(dto);
            return ResponseEntity.ok(new ApiMessage("Usuario creado correctamente"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ApiMessage(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new ApiMessage("Error interno: " + e.getMessage()));
        }
    }

    //Listar Todos los usuarios
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    //Editar usuarios
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{userId}")
    public ResponseEntity<ApiMessage> updateUser(
            @PathVariable UUID userId,
            @Valid @RequestBody AdminUpdateUserDto dto) {
        try {
            userService.updateUserByAdmin(userId, dto, roleRepository, passwordEncoder);
            return ResponseEntity.ok(new ApiMessage("Usuario actualizado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiMessage(e.getMessage()));
        }
    }

    // Eliminar usuario
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiMessage> deleteUser(@PathVariable UUID userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok(new ApiMessage("Usuario eliminado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiMessage(e.getMessage()));
        }
    }

}
