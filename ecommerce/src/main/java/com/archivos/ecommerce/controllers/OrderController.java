package com.archivos.ecommerce.controllers;

import com.archivos.ecommerce.dtos.orders.OrderRequestDto;
import com.archivos.ecommerce.dtos.orders.OrderResponseDto;
import com.archivos.ecommerce.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDto> create(@RequestBody OrderRequestDto dto){
        return ResponseEntity.ok(orderService.createOrder(dto));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponseDto>> getOrdersByUser(@PathVariable UUID userId){
        return ResponseEntity.ok(orderService.getOrdersByUserId(userId));
    }

}
