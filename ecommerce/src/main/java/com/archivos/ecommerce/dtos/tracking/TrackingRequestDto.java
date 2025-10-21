package com.archivos.ecommerce.dtos.tracking;

import java.util.Date;
import java.util.UUID;

public record TrackingRequestDto(
        UUID orderId,
        String trackingNumber,
        String shippingCompany,
        Date estimatedDelivery
){}
