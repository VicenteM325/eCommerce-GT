package com.archivos.ecommerce.dtos.orders;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record OrderRequestDto(
    UUID userId,
    String address
){}
