package com.archivos.ecommerce.dtos;

import java.math.BigDecimal;
import java.util.UUID;

public record CartItemDto(
    UUID cartItemId,
    UUID productId,
    String productName,
    Integer quantity,
    BigDecimal subtotal
){}
