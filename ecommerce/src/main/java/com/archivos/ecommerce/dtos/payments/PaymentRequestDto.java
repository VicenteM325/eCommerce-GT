package com.archivos.ecommerce.dtos.payments;

import java.math.BigDecimal;
import java.util.UUID;

public record PaymentRequestDto (
        UUID orderId,
        UUID creditCardId
){}

