package com.archivos.ecommerce.controllers;

import com.archivos.ecommerce.dtos.UserProductDto;
import com.archivos.ecommerce.enums.PublicationState;
import com.archivos.ecommerce.services.UserProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/user-products")
@RequiredArgsConstructor
public class UserProductController {

    private final UserProductService userProductService;
    @PreAuthorize("hasAnyRole('MODERATOR')")
    @GetMapping
    public ResponseEntity<List<UserProductDto>> getAll() {
        return ResponseEntity.ok(userProductService.getAll());
    }
    @PreAuthorize("hasAnyRole('COMMON')")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserProductDto>> getByUser(@PathVariable UUID userId) {
        return ResponseEntity.ok(userProductService.getByUser(userId));
    }

    @PostMapping
    public ResponseEntity<UserProductDto> create(@RequestParam UUID userId, @RequestParam UUID productId) {
        return ResponseEntity.status(201).body(userProductService.create(userId, productId));
    }

    // Listar productos pendientes de aprobación
    @PreAuthorize("hasAnyRole('MODERATOR')")
    @GetMapping("/pending")
    public ResponseEntity<List<UserProductDto>> getPending() {
        return ResponseEntity.ok(userProductService.getPending());
    }

    @PreAuthorize("hasAnyRole('MODERATOR')")
    @PutMapping("/{id}/moderate")
    public ResponseEntity<UserProductDto> moderate(
            @PathVariable UUID id,
            @RequestParam PublicationState state,
            @RequestParam(required = false) String comment
    ) {
        return ResponseEntity.ok(userProductService.moderate(id, state, comment));
    }
}
