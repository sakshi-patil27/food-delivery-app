package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class SecurityConfig {
	
	 @Autowired
	    private JwtAuthenticationFilter jwtAuthenticationFilter;

	    @Autowired
	    private UserDetailsService userDetailsService; 
	  @Bean
	    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
	        AuthenticationManagerBuilder authenticationManagerBuilder = 
	            http.getSharedObject(AuthenticationManagerBuilder.class);
	        return authenticationManagerBuilder.build();
	    }

	  @Bean
	  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	      http
	          .csrf().disable()
	          .authorizeHttpRequests(auth -> auth
	              .requestMatchers("/api/auth/register", "/api/auth/login").permitAll() // Allow registration and login
//	              .requestMatchers("/api/auth/validate").authenticated() // Require authentication for validation
	              .anyRequest().authenticated() // All other endpoints require authentication
	          )
	          .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	          .exceptionHandling(ex -> ex.authenticationEntryPoint((request, response, authException) -> {
	              response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	              response.getWriter().write("Unauthorized - Invalid Token");
	          }))
	          .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
	          .formLogin().disable(); // Disable default login form

	      return http.build();
	  }

	    // PasswordEncoder bean
	    @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
}
