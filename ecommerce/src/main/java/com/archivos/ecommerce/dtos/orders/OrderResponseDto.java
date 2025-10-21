package com.archivos.ecommerce.dtos.orders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record OrderResponseDto(
        UUID orderId,
        BigDecimal amount,
        String address,
        String status,
        List<OrderDetailDto> details
){}
