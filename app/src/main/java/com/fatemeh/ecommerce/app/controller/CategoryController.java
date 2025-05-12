package com.fatemeh.ecommerce.app.controller;

import com.fatemeh.ecommerce.app.model.Category;
import com.fatemeh.ecommerce.app.repository.CategoryRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    @PostMapping
    public Category createCategory(@RequestBody Category category){
        return categoryRepository.save(category);
    }

    @GetMapping
    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }

}
