package com.archivos.ecommerce.dtos.orders;

import java.math.BigDecimal;

public record OrderDetailDto(
        String productName,
        Integer quantity,
        BigDecimal price,
        BigDecimal subtotal
){}

