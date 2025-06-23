
package com.odontosimples.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
public class CorsConfig {

  @Value("${app.cors.allowed-origins}")
  private String allowedOrigins;

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new
    CorsConfiguration();

    List<String> origins = Arrays.asList 
    (allowedOrigins.split(","));
    configuration.setAllowedOrigins(origins);

    configuration.setAllowedOrigins(Arrays.asList("GET", "POST", "DELETE", "OPTIONS", "PATCH"));

    configuration.setAllowedHeaders(Arrays.asList("*"));

    configuration.setExposedHeaders(Arrays.asList("Authorization", "Content-Type"));

    UrlBasedCorsConfigurationSource source = new
    UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);


    return source;
  }

  
}
