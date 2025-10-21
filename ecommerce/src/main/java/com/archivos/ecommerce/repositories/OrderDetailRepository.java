package com.archivos.ecommerce.repositories;

import com.archivos.ecommerce.entities.Order;
import com.archivos.ecommerce.entities.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, UUID> {
    List<OrderDetail> findByOrder(Order order);
}
