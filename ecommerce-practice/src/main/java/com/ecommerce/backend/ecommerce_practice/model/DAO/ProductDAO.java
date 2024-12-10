package com.ecommerce.backend.ecommerce_practice.model.DAO;

import com.ecommerce.backend.ecommerce_practice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDAO extends JpaRepository<Product, Integer> {
}
