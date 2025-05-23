package com.fatemeh.ecommerce.app.repository;

import com.fatemeh.ecommerce.app.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // Returns all products that belong to the specified category ID
    List<Product> findByCategoryId(Integer categoryId);
}
