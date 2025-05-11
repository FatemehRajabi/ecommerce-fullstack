package com.fatemeh.ecommerce.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * Web configuration to expose the local /uploads directory as a static resource.
 * This allows uploaded images to be accessed directly via URLs like:
 * http://localhost:8080/uploads/filename.jpg
 */


@SpringBootApplication
public class EcommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);
	}

}

