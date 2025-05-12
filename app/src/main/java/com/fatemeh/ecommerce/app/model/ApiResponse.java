package com.fatemeh.ecommerce.app.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Standard response structure for API endpoints.
 * Includes a message, success flag, and timestamp for consistency in all responses.
 * Used to wrap both success and error messages in a readable format.
 */

@Data
public class ApiResponse {

    private String message;
    private boolean success;
    private LocalDateTime timestamp;

}
