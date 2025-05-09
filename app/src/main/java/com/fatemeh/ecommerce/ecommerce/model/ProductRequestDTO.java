package com.fatemeh.ecommerce.ecommerce.model;

import lombok.Data;

/**
 * DTO (Data Transfer Object) for creating a new Product.
 * This class receives data from the client side, including the category ID,
 * instead of requiring a full Category object.
 */


@Data
public class ProductRequestDTO {
    private String name;
    private String description;
    private Double price;
    private String imageUrl;
    private Integer stock;
    private Integer categoryId;
}
