package com.fatemeh.ecommerce.ecommerce.controller;

import com.fatemeh.ecommerce.ecommerce.model.Product;
import com.fatemeh.ecommerce.ecommerce.repository.ProductRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @GetMapping
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    @PostMapping
    public Product postProduct(@RequestBody Product product){
        return productRepository.save(product);
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id){

//      productRepository.findById(id) returns an Optional<Product>
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product){
        Product productToUpdate = getProductById(id);

        productToUpdate.setName(product.getName());
        productToUpdate.setDescription(product.getDescription());
        productToUpdate.setStock(product.getStock());
        productToUpdate.setPrice(product.getPrice());
        productToUpdate.setImageUrl(product.getImageUrl());

        productRepository.save(productToUpdate);

        return productToUpdate;
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id){
        getProductById(id);
        productRepository.deleteById(id);
    }
}















