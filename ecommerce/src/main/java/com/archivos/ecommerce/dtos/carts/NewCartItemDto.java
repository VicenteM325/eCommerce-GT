package com.archivos.ecommerce.dtos.carts;

import java.util.UUID;

public record NewCartItemDto(
        UUID productId,
        Integer quantity
){}

