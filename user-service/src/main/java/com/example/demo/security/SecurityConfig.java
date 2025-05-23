package com.example.demo.security;

import java.util.List;

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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.http.HttpMethod;

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
	          .csrf(csrf -> csrf.disable())
	          .cors(cors -> cors.configurationSource(corsConfigurationSource()))
	          .authorizeHttpRequests(auth -> auth
	              // Permitting OPTIONS requests for all endpoints
	              .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
	              // These endpoints are open without authentication
	              .requestMatchers("/auth/register", "/auth/login","/auth/send-otp","/auth/verify-otp").permitAll()
	              .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
	              // All other requests require authentication
	              .anyRequest().authenticated()
	          )
	          .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	          .exceptionHandling(ex -> ex
	              .authenticationEntryPoint((request, response, authException) -> {
	                  response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	                  response.getWriter().write("Unauthorized - Invalid Token");
	              })
	          )
	          .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
	          .formLogin(form -> form.disable());

	      return http.build();
	  }


	    // PasswordEncoder bean
	    @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
	    
	    @Bean
	    public CorsConfigurationSource corsConfigurationSource() {
	        CorsConfiguration config = new CorsConfiguration();
	        config.setAllowedOriginPatterns(List.of("http://localhost:5173", "https://grabobite.netlify.app"));
	        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
	        config.setAllowedHeaders(List.of("*"));
	        config.setAllowedOriginPatterns(List.of("*")); // or include localhost if not already
	        config.setAllowCredentials(true);

	        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	        source.registerCorsConfiguration("/**", config);
	        return source;
	    }

}



