package com.archivos.ecommerce.controllers;

import com.archivos.ecommerce.dtos.payments.PaymentRequestDto;
import com.archivos.ecommerce.dtos.payments.PaymentResponseDto;
import com.archivos.ecommerce.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponseDto> create(@RequestBody PaymentRequestDto dto){
        return ResponseEntity.ok(paymentService.createPayment(dto));
    }

    @PutMapping("/{paymentId}/complete")
    public ResponseEntity<PaymentResponseDto> complete(@PathVariable UUID paymentId){
        return ResponseEntity.ok(paymentService.completePayment(paymentId));
    }
}
