package org.example.authenticationandauthorization.repository;

import org.example.authenticationandauthorization.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
