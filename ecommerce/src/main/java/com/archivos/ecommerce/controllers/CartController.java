package com.archivos.ecommerce.controllers;

import com.archivos.ecommerce.dtos.carts.CartDto;
import com.archivos.ecommerce.dtos.carts.NewCartItemDto;
import com.archivos.ecommerce.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    //Obtener el carrito de un usuario
    @GetMapping("/{userId}")
    public ResponseEntity<CartDto> getCart(@PathVariable UUID userId){
        return ResponseEntity.ok(cartService.getCartByUserId(userId));
    }

    //Agegar producto al carrito
    @PostMapping("/{userId}/add")
    public ResponseEntity<CartDto> addItem(@PathVariable UUID userId, @RequestBody NewCartItemDto dto){
        return ResponseEntity.ok(cartService.addItemToCart(userId, dto));
    }

    //Eliminar producto del carrito
    @DeleteMapping("/{userId}/remove/{productId}")
    public ResponseEntity<CartDto> removeItem(@PathVariable UUID userId, @PathVariable UUID productId){
        return ResponseEntity.ok(cartService.removeItem(userId, productId));
    }

    //Vaciar el carrito
    @DeleteMapping("/{userId}/clear")
    public ResponseEntity<CartDto> clearCart(@PathVariable UUID userId){
        cartService.clearCart(userId);
        return ResponseEntity.noContent().build();
    }
}
