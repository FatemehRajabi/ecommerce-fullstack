package com.fatemeh.ecommerce.app.repository;

import com.fatemeh.ecommerce.app.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
