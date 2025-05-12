package com.fatemeh.ecommerce.app.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String password;

//    This tells JPA to store the enum as a string
    @Enumerated(EnumType.STRING)
    private Role role;
}
