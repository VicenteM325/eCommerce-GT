package com.archivos.ecommerce.services;

import com.archivos.ecommerce.dtos.payments.PaymentRequestDto;
import com.archivos.ecommerce.dtos.payments.PaymentResponseDto;
import com.archivos.ecommerce.entities.*;
import com.archivos.ecommerce.repositories.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final CreditCardRepository creditCardRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final TrackingDetailRepository trackingDetailRepository;

    @Transactional
    public PaymentResponseDto createPayment(PaymentRequestDto dto){
        Order order = orderRepository.findById(dto.orderId())
                .orElseThrow(()-> new RuntimeException("Orden no encontrada"));

        CreditCard card = creditCardRepository.findById(dto.creditCardId())
                .orElseThrow(()-> new RuntimeException("Tarjeta no encontrada"));

        if(paymentRepository.findByOrder(order).isPresent()){
            throw new RuntimeException("La orden ya tiene un pago registrado");
        }
        var totalOrden = order.getAmount();

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setCreditCard(card);
        payment.setAmount(totalOrden);
        payment.setPaymentStatus("COMPLETADO");
        payment.setCreatedAt(new Date());
        payment.setPaymentMethod("TARJETA DE CREDITO");

        payment = paymentRepository.save(payment);

        OrderStatus paidStatus = orderStatusRepository.findByStatusName("Pagada")
                .orElseThrow(() -> new RuntimeException("Estado de orden 'Pagada' no encontrado"));
        order.setOrderStatus(paidStatus);
        order.setUpdatedAt(new Date());
        orderRepository.save(order);

        //Crea el seguimiento de la orden
        trackingDetailRepository.save(TrackingDetail.builder()
                .order(order)
                .trackingNumber("TRK-" + UUID.randomUUID().toString().substring(0,8))
                .shippingCompany("Ecommerce Express")
                .estimatedDelivery(new Date(System.currentTimeMillis() + 5*24*60*60*1000))
                .deliveryStatus("EN CURSO")
                .build());

        return new PaymentResponseDto(
                payment.getPaymentId(),
                order.getOrderId(),
                payment.getAmount(),
                payment.getPaymentMethod(),
                payment.getPaymentStatus(),
                payment.getCreatedAt()
        );

    }

    @Transactional
    public PaymentResponseDto completePayment(UUID paymentId){
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(()-> new RuntimeException("Pago no encontrado"));

        payment.setPaymentStatus("COMPLETADO");
        payment.setUpdatedAt(new Date());
        paymentRepository.save(payment);

        //Actualizar el estado de la orden
        OrderStatus paidStatus = orderStatusRepository.findByStatusName("Pagada")
                .orElseThrow(()-> new RuntimeException("Estado de orden no encontrado"));

        Order order = payment.getOrder();
        order.setOrderStatus(paidStatus);
        order.setUpdatedAt(new Date());

        return new PaymentResponseDto(
                payment.getPaymentId(),
                order.getOrderId(),
                payment.getAmount(),
                payment.getPaymentMethod(),
                payment.getPaymentStatus(),
                payment.getUpdatedAt()
        );
    }
}
