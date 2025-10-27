package com.archivos.ecommerce.repositories;

import com.archivos.ecommerce.entities.Product;
import com.archivos.ecommerce.entities.User;
import com.archivos.ecommerce.entities.UserProduct;
import com.archivos.ecommerce.enums.PublicationState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserProductRepository extends JpaRepository<UserProduct, UUID> {
    List<UserProduct> findByUserId(User user);
    Optional<UserProduct> findByUserIdAndProductId(User user, Product product);
    List<UserProduct> findByState(PublicationState state);

}
