package com.archivos.ecommerce.entities;

import com.archivos.ecommerce.enums.PublicationState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user_product")
public class UserProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product productId;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private PublicationState state = PublicationState.PENDIENTE;

    @Column(length = 500)
    private String moderationComment;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}