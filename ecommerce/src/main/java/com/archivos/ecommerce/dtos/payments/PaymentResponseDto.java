package com.archivos.ecommerce.dtos.payments;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public record PaymentResponseDto(
        UUID paymentId,
        UUID orderId,
        BigDecimal amount,
        String paymentMethod,
        String paymentStatus,
        Date paymentDate
){}
