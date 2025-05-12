package com.fatemeh.ecommerce.app.config;

import com.fatemeh.ecommerce.app.security.JwtAuthenticationFilter;
import com.fatemeh.ecommerce.app.security.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configures application-wide security settings using Spring Security.
 *
 * Responsibilities:
 * - Disables CSRF protection for stateless JWT authentication
 * - Configures session management to be stateless (no HTTP sessions)
 * - Defines public and protected API endpoints
 * - Registers a custom JwtAuthenticationFilter to validate JWT tokens
 *   before processing authentication requests
 *
 * This configuration ensures that only authenticated users with valid
 * JWTs can access secured endpoints, while allowing public access to
 * endpoints like login, signup, and public product data.
 */

@Configuration
@EnableMethodSecurity
public class SecurityConfig{

    JwtUtil jwtUtil;
    UserDetailsService userDetailsService;

    public SecurityConfig(JwtUtil jwtUtil, UserDetailsService userDetailsService){
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtUtil, userDetailsService);

        http.csrf(csrf -> csrf.disable());
        http
                .securityContext(context -> context.requireExplicitSave(false))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/signup", "/api/auth/login", "/api/products", "/uploads/**").permitAll()
                .anyRequest().authenticated()
        );
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
