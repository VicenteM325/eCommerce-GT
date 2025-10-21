package com.archivos.ecommerce.dtos.tracking;

import java.util.Date;
import java.util.UUID;

public record TrackingResponseDto(
        UUID trackingDetailId,
        UUID orderId,
        String trackingNumber,
        String shippingCompany,
        Date estimatedDelivery,
        String deliveryStatus,
        Date createdAt,
        Date updatedAt
){}