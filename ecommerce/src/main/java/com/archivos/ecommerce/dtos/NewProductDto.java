package com.archivos.ecommerce.dtos;

import java.math.BigDecimal;

public record NewProductDto(
        String name,
        String description,
        String picture,
        BigDecimal price,
        Integer stock,
        Integer categoryId,
        Integer stateId
) {}
