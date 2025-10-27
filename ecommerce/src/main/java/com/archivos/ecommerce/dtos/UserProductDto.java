package com.archivos.ecommerce.dtos;
import com.archivos.ecommerce.enums.PublicationState;

import java.util.UUID;

public record UserProductDto (
        UUID userProductId,
        UUID userId,
        String userName,
        UUID productId,
        String productName,
        PublicationState state,
        String moderationComment
){ }
