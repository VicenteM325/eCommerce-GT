package com.archivos.ecommerce.repositories;

import com.archivos.ecommerce.entities.Role;
import com.archivos.ecommerce.enums.RoleList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByName(RoleList name);
}
