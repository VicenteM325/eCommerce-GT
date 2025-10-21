package com.archivos.ecommerce.repositories;

import com.archivos.ecommerce.entities.Order;
import com.archivos.ecommerce.entities.TrackingDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TrackingDetailRepository extends JpaRepository<TrackingDetail, UUID> {
    Optional<TrackingDetail> findByOrder(Order order);
    Optional<TrackingDetail> findByTrackingNumber(String trackingNumber);
}
