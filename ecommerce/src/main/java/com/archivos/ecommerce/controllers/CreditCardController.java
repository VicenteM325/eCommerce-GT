package com.archivos.ecommerce.controllers;

import com.archivos.ecommerce.dtos.creditcards.CreditCardRequestDto;
import com.archivos.ecommerce.dtos.creditcards.CreditCardResponseDto;
import com.archivos.ecommerce.services.CreditCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/credit-cards")
@RequiredArgsConstructor
public class CreditCardController {

    private final CreditCardService service;

    @PostMapping
    public ResponseEntity<CreditCardResponseDto> create(@RequestBody CreditCardRequestDto dto){
        return ResponseEntity.ok(service.createCreditCard(dto));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CreditCardResponseDto>> getByUser(@PathVariable UUID userId){
        return ResponseEntity.ok(service.getCardsByUser(userId));
    }

    @DeleteMapping("/{cardId}")
    public ResponseEntity<Void> delete(@PathVariable UUID cardId){
        service.deletedCard(cardId);
        return ResponseEntity.noContent().build();
    }
}
