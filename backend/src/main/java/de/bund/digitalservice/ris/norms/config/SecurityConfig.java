package de.bund.digitalservice.ris.norms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuration class for defining security settings in the application. This class is annotated
 * with {@link Configuration} and {@link EnableWebSecurity} to indicate that it provides security
 * configuration for a web-based application.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

  /**
   * Configures security settings for specific HTTP requests.
   *
   * @param http The {@link HttpSecurity} object to configure security settings.
   * @return A {@link SecurityFilterChain} configured with security settings.
   * @throws Exception If an exception occurs during security configuration.
   */
  @Bean
  public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(
            authorize ->
                authorize
                    .requestMatchers(
                        "/.well-known/security.txt",
                        "/favicon.svg",
                        "/actuator/health",
                        "/actuator/health/readiness",
                        "/actuator/health/liveness",
                        "/api/**",
                        "/index.html",
                        "/",
                        "/assets/**")
                    .permitAll()
                    .anyRequest()
                    .denyAll())
        .csrf(AbstractHttpConfigurer::disable);
    return http.build();
  }
}
