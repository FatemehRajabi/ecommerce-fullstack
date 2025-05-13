package com.fatemeh.ecommerce.app.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filter that intercepts incoming HTTP requests to validate JWT tokens.
 *
 * Responsibilities:
 * - Extracts the token from the Authorization header
 * - Validates the token using JwtUtil
 * - Loads user details from the database if the token is valid
 * - Sets the authenticated user in the SecurityContext
 *
 * This filter ensures that secured endpoints can recognize and trust
 * stateless authentication via JWT.
 */

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    JwtUtil jwtUtil;
    UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService){
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Filters each incoming HTTP request to check for a valid JWT in the Authorization header.
     *
     * Steps:
     * 1. Extracts the token from the header if it starts with "Bearer ".
     * 2. Validates the token using JwtUtil.
     * 3. If valid and no existing authentication, loads user details and sets authentication.
     * 4. Continues the filter chain regardless of authentication outcome.
     *
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getServletPath();
        if (path.equals("/api/auth/login") || path.equals("/api/auth/signup")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7); // Removes "Bearer "

        if (jwtUtil.validateToken(token)) {
            String username = jwtUtil.extractUsername(token);
            if(SecurityContextHolder.getContext().getAuthentication() == null){
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        filterChain.doFilter(request, response);
    }
}
