package com.archivos.ecommerce.dtos;


import java.math.BigDecimal;
import java.util.UUID;

public record ProductDto (
    UUID productId,
    String name,
    String description,
    String picture,
    BigDecimal price,
    Integer stock,
    String categoryName,
    String stateName
) {}