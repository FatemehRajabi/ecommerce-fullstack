package com.fatemeh.ecommerce.ecommerce.repository;

import com.fatemeh.ecommerce.ecommerce.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
