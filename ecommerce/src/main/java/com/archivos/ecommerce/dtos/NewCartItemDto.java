package com.archivos.ecommerce.dtos;

import java.util.UUID;

public record NewCartItemDto(
        UUID productId,
        Integer quantity
){}

