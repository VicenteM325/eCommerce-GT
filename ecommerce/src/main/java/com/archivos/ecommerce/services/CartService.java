package com.archivos.ecommerce.services;

import com.archivos.ecommerce.dtos.CartDto;
import com.archivos.ecommerce.dtos.CartItemDto;
import com.archivos.ecommerce.dtos.NewCartItemDto;
import com.archivos.ecommerce.entities.Cart;
import com.archivos.ecommerce.entities.CartItem;
import com.archivos.ecommerce.entities.Product;
import com.archivos.ecommerce.entities.User;
import com.archivos.ecommerce.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserEntityRepository userRepository;

    //Obtiene el carrito del usuario
    public CartDto getCartByUserId(UUID userId){
        Cart cart = cartRepository.findByUser_UserId(userId)
                .orElseGet(() -> createNewCart(userId));
        return convertToDto(cart);
    }

    //Crea nuevo carrito si no existe pero lo crea vacío
    private Cart createNewCart(UUID userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Cart cart = new Cart();
        cart.setUser(user);
        cart.setDateCreate(new Date());
        return cartRepository.save(cart);
    }

    //Agrega producto al carrito
    public CartDto addItemToCart(UUID userId, NewCartItemDto dto){
        Cart cart = cartRepository.findByUser_UserId(userId)
                .orElseGet(()-> createNewCart(userId));

        Product product = productRepository.findById(dto.productId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        Optional<CartItem> existingItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getProductId().equals(dto.productId()))
                .findFirst();

        if(existingItem.isPresent()){
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + dto.quantity());
            item.setSubtotal(product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        } else  {
            CartItem newitem = new CartItem();
            newitem.setCart(cart);
            newitem.setProduct(product);
            newitem.setQuantity(dto.quantity());
            newitem.setSubtotal(product.getPrice().multiply(BigDecimal.valueOf(dto.quantity())));

            cart.getCartItems().add(newitem);
        }


        Cart updated = cartRepository.save(cart);
        return convertToDto(updated);
    }

    // Eliminar un producto del carrito
    public CartDto removeItem(UUID userId, UUID productId){
        Cart cart = cartRepository.findByUser_UserId(userId)
                .orElseThrow(()-> new RuntimeException("Carrito no encontrado"));

        cart.getCartItems().removeIf(item -> item.getProduct().getProductId().equals(productId));

        Cart updated = cartRepository.save(cart);
        return convertToDto(updated);
    }

    // Vaciar el carrito
    public void clearCart(UUID userId){
        Cart cart = cartRepository.findByUser_UserId(userId)
                .orElseThrow(()-> new RuntimeException("Carrito no encontrado"));

        cart.getCartItems().clear();
        cartRepository.save(cart);
    }

    //Convierte la entidad a DTO
    private CartDto convertToDto(Cart cart){
        List<CartItemDto> items = cart.getCartItems().stream()
                .map(item -> new CartItemDto(
                        item.getCartItemId(),
                        item.getProduct().getProductId(),
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getSubtotal()
                ))
                .collect(Collectors.toList());

        BigDecimal total = items.stream()
                .map(CartItemDto::subtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new CartDto(
                cart.getCartId(),
                cart.getUser().getUserId(),
                cart.getDateCreate(),
                items,
                total
        );
    }
}
