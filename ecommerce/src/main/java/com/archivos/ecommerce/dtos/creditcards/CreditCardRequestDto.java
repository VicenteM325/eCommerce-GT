package com.archivos.ecommerce.dtos.creditcards;

import java.util.Date;
import java.util.UUID;

public record CreditCardRequestDto(
        String cardNumber,
        String cardName,
        Date expirationDate,
        String cvv,
        UUID userId
){}
