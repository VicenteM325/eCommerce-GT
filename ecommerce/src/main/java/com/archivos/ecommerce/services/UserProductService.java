package com.archivos.ecommerce.services;

import com.archivos.ecommerce.dtos.UserProductDto;
import com.archivos.ecommerce.entities.User;
import com.archivos.ecommerce.entities.Product;
import com.archivos.ecommerce.entities.UserProduct;
import com.archivos.ecommerce.enums.PublicationState;
import com.archivos.ecommerce.repositories.UserEntityRepository;
import com.archivos.ecommerce.repositories.UserProductRepository;
import com.archivos.ecommerce.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserProductService {

    private final UserProductRepository userProductRepository;
    private final UserEntityRepository userRepository;
    private final ProductRepository productRepository;

    // Crear producto para un usuario
    public UserProductDto create(UUID userId, UUID productId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        UserProduct userProduct = new UserProduct();
        userProduct.setUserId(user);
        userProduct.setProductId(product);
        userProduct.setState(PublicationState.PENDIENTE);

        UserProduct saved = userProductRepository.save(userProduct);
        return convertToDto(saved);
    }

    // Listar todos
    public List<UserProductDto> getAll() {
        return userProductRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


    public List<UserProductDto> getPending() {
        return userProductRepository.findByState(PublicationState.PENDIENTE)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Listar por usuario
    public List<UserProductDto> getByUser(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return userProductRepository.findByUserId(user)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Aprobar o rechazar producto
    public UserProductDto moderate(UUID userProductId, PublicationState state, String comment) {
        UserProduct userProduct = userProductRepository.findById(userProductId)
                .orElseThrow(() -> new RuntimeException("Registro no encontrado"));
        if (state != PublicationState.ACEPTADO && state != PublicationState.RECHAZADO) {
            throw new IllegalArgumentException("Solo se puede aprobar o rechazar una publicación");
        }

        userProduct.setState(state);
        userProduct.setModerationComment(comment != null ? comment : "");

        UserProduct updated = userProductRepository.save(userProduct);
        return convertToDto(updated);
    }

    private UserProductDto convertToDto(UserProduct up) {
        return new UserProductDto(
                up.getId(),
                up.getUserId().getUserId(),
                up.getUserId().getName(),
                up.getProductId().getProductId(),
                up.getProductId().getName(),
                up.getState(),
                up.getModerationComment()
        );
    }
}
