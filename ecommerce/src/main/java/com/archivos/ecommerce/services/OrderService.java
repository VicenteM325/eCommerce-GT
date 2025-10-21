package com.archivos.ecommerce.services;


import com.archivos.ecommerce.dtos.orders.OrderDetailDto;
import com.archivos.ecommerce.dtos.orders.OrderRequestDto;
import com.archivos.ecommerce.dtos.orders.OrderResponseDto;
import com.archivos.ecommerce.entities.*;
import com.archivos.ecommerce.repositories.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class OrderService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final UserEntityRepository userEntityRepository;

    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto request){
        User user = userEntityRepository.findById(request.userId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Cart cart = cartRepository.findByUser_UserId(user.getUserId())
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        List<CartItem> items = cartItemRepository.findByCart_CartId(cart.getCartId());
        if(items.isEmpty())throw new RuntimeException("El carrito esta vacio");

        OrderStatus status = orderStatusRepository.findByStatusName("Pendiente")
                .orElseThrow(() -> new RuntimeException("Estado de orden no encontrado"));

        Order order = new Order();
        order.setUser(user);
        order.setAmount(BigDecimal.ZERO);
        order.setAddress(request.address());
        order.setOrderStatus(status);

        BigDecimal total = BigDecimal.ZERO;

        order = orderRepository.save(order);

        for(CartItem item : items){
            OrderDetail detail = new OrderDetail();
            detail.setOrder(order);
            detail.setProduct(item.getProduct());
            detail.setQuantity(item.getQuantity());
            detail.setPrice(item.getProduct().getPrice());
            detail.setSubtotal(item.getProduct().getPrice()
                    .multiply(BigDecimal.valueOf(item.getQuantity())));
            orderDetailRepository.save(detail);

            total = total.add(detail.getSubtotal());
        }

        order.setAmount(total);
        orderRepository.save(order);

        //Limpiar carrito
        cartItemRepository.deleteAll(items);

        return new OrderResponseDto(
                order.getOrderId(),
                total,
                order.getAddress(),
                status.getStatusName(),
                items.stream()
                        .map(i -> new OrderDetailDto(
                                i.getProduct().getName(),
                                i.getQuantity(),
                                i.getProduct().getPrice(),
                                i.getProduct().getPrice()
                                        .multiply(BigDecimal.valueOf(i.getQuantity()))
                        ))
                        .toList()
        );
    }

    public List<OrderResponseDto> getOrdersByUserId(UUID userId){
        User user = userEntityRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return orderRepository.findByUser(user).stream()
                .map(o -> new OrderResponseDto(
                        o.getOrderId(),
                        o.getAmount(),
                        o.getAddress(),
                        o.getOrderStatus().getStatusName(),
                        o.getOrderDetails().stream()
                                .map(d -> new OrderDetailDto(
                                        d.getProduct().getName(),
                                        d.getQuantity(),
                                        d.getPrice(),
                                        d.getSubtotal()))
                                .toList()
                )).toList();
    }
}
