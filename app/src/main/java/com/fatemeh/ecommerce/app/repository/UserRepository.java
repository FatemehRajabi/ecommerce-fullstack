package com.fatemeh.ecommerce.app.repository;

import com.fatemeh.ecommerce.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

//    Optional<User>  helps avoid null and makes it clear that the user might not exist
    Optional<User> findByUsername(String username);
}
