package com.archivos.ecommerce.services;

import com.archivos.ecommerce.dtos.creditcards.CreditCardRequestDto;
import com.archivos.ecommerce.dtos.creditcards.CreditCardResponseDto;
import com.archivos.ecommerce.entities.CreditCard;
import com.archivos.ecommerce.entities.User;
import com.archivos.ecommerce.repositories.CreditCardRepository;
import com.archivos.ecommerce.repositories.UserEntityRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreditCardService {

    private final CreditCardRepository creditCardRepository;
    private final UserEntityRepository userRepository;

    @Transactional
    public CreditCardResponseDto createCreditCard(CreditCardRequestDto dto){
        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        CreditCard card = new CreditCard();
        card.setCardNumber(dto.cardNumber());
        card.setCardName(dto.cardName());
        card.setExpirationDate(dto.expirationDate());
        card.setCvv(dto.cvv());
        card.setUser(user);

        card = creditCardRepository.save(card);

        return new CreditCardResponseDto(
                card.getCreditCardId(),
                card.getCardNumber(),
                card.getCardName(),
                card.getExpirationDate()
        );
    }

    public List<CreditCardResponseDto> getCardsByUser(UUID userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return creditCardRepository.findByUser(user).stream()
                .map(c -> new CreditCardResponseDto(
                        c.getCreditCardId(),
                        c.getCardNumber(),
                        c.getCardName(),
                        c.getExpirationDate()
                ))
                .toList();
    }

    @Transactional
    public void deletedCard(UUID cardId){
        creditCardRepository.deleteById(cardId);
    }
}
