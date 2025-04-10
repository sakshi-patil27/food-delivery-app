package com.example.demo.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()

            // Public Auth Routes (No Security Filter)
            .route("user-service", r -> r.path("/auth/**")
                .uri("https://user-service-6eg1.onrender.com"))

            // Secure Restaurant Service
            .route("restaurant-service", r -> r.path("/restaurant/**")
                .filters(f -> f.filter(jwtAuthenticationFilter)) // 👈 Attach filter
                .uri("http://localhost:8082"))

            // Secure Order Service
            .route("order-service", r -> r.path("/order/**")
                .filters(f -> f.filter(jwtAuthenticationFilter))
                .uri("http://localhost:8083"))

            .build();
    }
}

