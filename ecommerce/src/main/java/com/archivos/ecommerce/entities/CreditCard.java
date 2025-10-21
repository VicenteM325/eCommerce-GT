package com.archivos.ecommerce.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Security;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "credit_card")
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "credit_card_id", updatable = false, nullable = false)
    private UUID creditCardId;

    @Column(name = "card_number", nullable = false)
    private String cardNumber;

    @Column(name = "card_name", nullable = false)
    private String cardName;

    @Column(name = "expiration_date", nullable = false)
    private Date expirationDate;

    @Column(name = "cvv", nullable = false, length = 4)
    private String cvv;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "created_at")
    private Date createdAt = new Date();

    @Column(name = "updated_at")
    private Date updatedAt = new Date();
}
