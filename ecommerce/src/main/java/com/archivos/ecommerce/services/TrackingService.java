package com.archivos.ecommerce.services;

import com.archivos.ecommerce.dtos.tracking.TrackingRequestDto;
import com.archivos.ecommerce.dtos.tracking.TrackingResponseDto;
import com.archivos.ecommerce.entities.Order;
import com.archivos.ecommerce.entities.TrackingDetail;
import com.archivos.ecommerce.repositories.OrderRepository;
import com.archivos.ecommerce.repositories.TrackingDetailRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TrackingService {

    private final TrackingDetailRepository trackingDetailRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public TrackingResponseDto createTracking(TrackingRequestDto dto){
        Order order = orderRepository.findById(dto.orderId())
                .orElseThrow(()-> new RuntimeException("Orden no encontrada"));

        if(trackingDetailRepository.findByOrder(order).isPresent()){
            throw new RuntimeException("La orden ya tiene un seguimiento asignado");
        }

        TrackingDetail tracking = TrackingDetail.builder()
                .order(order)
                .trackingNumber(dto.trackingNumber())
                .shippingCompany(dto.shippingCompany())
                .estimatedDelivery(dto.estimatedDelivery())
                .deliveryStatus("EN CURSO")
                .createdAt(new Date())
                .build();

        return new TrackingResponseDto(
                tracking.getTrackingDetailId(),
                order.getOrderId(),
                tracking.getTrackingNumber(),
                tracking.getShippingCompany(),
                tracking.getEstimatedDelivery(),
                tracking.getDeliveryStatus(),
                tracking.getCreatedAt(),
                tracking.getUpdatedAt()
        );
    }

    @Transactional
    public TrackingResponseDto updateDeliveryStatus(UUID trackingId, String status){
        TrackingDetail tracking = trackingDetailRepository.findById(trackingId)
                .orElseThrow(()-> new RuntimeException("Seguimiento no encontrado"));

        tracking.setDeliveryStatus(status);
        tracking.setUpdatedAt(new Date());
        trackingDetailRepository.save(tracking);

        return new TrackingResponseDto(
                tracking.getTrackingDetailId(),
                tracking.getOrder().getOrderId(),
                tracking.getTrackingNumber(),
                tracking.getShippingCompany(),
                tracking.getEstimatedDelivery(),
                tracking.getDeliveryStatus(),
                tracking.getCreatedAt(),
                tracking.getUpdatedAt()
        );
    }


}
