package com.archivos.ecommerce.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tracking_detail")
public class TrackingDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID trackingDetailId;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "tracking_number", nullable = false, unique = true)
    private String trackingNumber;

    @Column(name = "shipping_company")
    private String shippingCompany;

    @Temporal(TemporalType.DATE)
    @Column(name = "estimated_delivery")
    private Date estimatedDelivery;

    @Column(name = "delivery_status")
    private String deliveryStatus = "EN CURSO";

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt = new Date();
}
