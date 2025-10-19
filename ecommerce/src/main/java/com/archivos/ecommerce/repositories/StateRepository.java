package com.archivos.ecommerce.repositories;

import com.archivos.ecommerce.entities.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateRepository extends JpaRepository<State, Integer> {
    boolean existsByName(String name);
}
