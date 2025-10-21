package com.archivos.ecommerce.repositories;

import com.archivos.ecommerce.entities.CreditCard;
import com.archivos.ecommerce.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, UUID> {
    List<CreditCard> findByUser(User user);
}
