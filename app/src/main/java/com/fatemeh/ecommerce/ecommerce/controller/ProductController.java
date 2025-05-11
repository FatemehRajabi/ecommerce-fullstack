package com.fatemeh.ecommerce.ecommerce.controller;

import com.fatemeh.ecommerce.ecommerce.model.Category;
import com.fatemeh.ecommerce.ecommerce.model.Product;
import com.fatemeh.ecommerce.ecommerce.model.ProductRequestDTO;
import com.fatemeh.ecommerce.ecommerce.repository.CategoryRepository;
import com.fatemeh.ecommerce.ecommerce.repository.ProductRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {


    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductController(ProductRepository productRepository, CategoryRepository categoryRepository){
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public List<Product> getAllProducts(@RequestParam(required = false) Integer categoryId){
        if (categoryId != null) {
            return productRepository.findByCategoryId(categoryId);
        }
        return productRepository.findAll();
    }


    /**
     * Creates a new Product using data from a ProductRequestDTO.
     * Retrieves the Category by ID and links it to the product.
     * Saves the product to the database and returns the saved object.
     */
    @PostMapping
    public Product postProduct(@RequestBody ProductRequestDTO dto){
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found."));

        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setImageUrl(dto.getImageUrl());
        product.setStock(dto.getStock());
        product.setCategory(category);

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

    @DeleteMapping
    public void deleteAllProducts() {
        productRepository.deleteAll();
    }

    /**
     * Handles uploading a product image and saving it to the local filesystem:
     * - Accepts a multipart file from the request
     * - Builds a path to save the file under the /uploads directory
     * - Transfers the file to the destination path
     */
    @PostMapping("/{id}/image")
    public Product uploadImage(@PathVariable Long id, @RequestParam MultipartFile file) throws IOException {
        Product product = getProductById(id);

        String uploadDirectory = System.getProperty("user.dir") + "/uploads/";
        String fileName = file.getOriginalFilename();
        String fullPath = uploadDirectory + fileName;

        File uploadFolder = new File(uploadDirectory);
        if (!uploadFolder.exists()) {
            uploadFolder.mkdirs(); // creates the folder if it doesn't exist
        }

        File destination = new File(fullPath);
        file.transferTo(destination);

        product.setImageUrl("/uploads/" + fileName);
        return productRepository.save(product);
    }
}















