package com.archivos.ecommerce.dtos.creditcards;

import java.util.Date;
import java.util.UUID;

public record CreditCardResponseDto(
        UUID creditCardId,
        String cardNumber,
        String cardName,
        Date expirationDate
){}
