package com.archivos.ecommerce.dtos;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public record CartDto(
        UUID cartId,
        UUID userId,
        Date dateCreate,
        List<CartItemDto> items,
        BigDecimal total
){}
