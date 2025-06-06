package de.bund.digitalservice.ris.norms.config;

import de.bund.digitalservice.ris.norms.domain.entity.Roles;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;

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
    http
      .authorizeHttpRequests(authorize ->
        authorize
          .requestMatchers(
            "/.well-known/security.txt",
            "/actuator/health/**",
            "/actuator/prometheus",
            "/environment",
            // Frontend routes
            "/",
            "/app/**"
          )
          .permitAll()
          // First restrict /api/v1/external/** to e-V user
          .requestMatchers("/api/v1/external/**")
          .hasRole(Roles.EVERKUENDUNG_USER)
          // Second restrict the rest of /api/v1/** to norms user, excluding /api/v1/external/** because matched before
          .requestMatchers("/api/v1/**")
          .hasRole(Roles.NORMS_USER)
          .anyRequest() // shall prevent an (authenticated) user to access accidentally available urls
          .denyAll()
      )
      .exceptionHandling(configurer ->
        configurer.defaultAuthenticationEntryPointFor(
          new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED),
          PathPatternRequestMatcher.withDefaults().matcher("/api/**")
        )
      )
      .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
      .csrf(AbstractHttpConfigurer::disable)
      .cors(Customizer.withDefaults())
      .sessionManagement(sessionManagement ->
        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      );
    return http.build();
  }

  /**
   * A custom {@link JwtAuthenticationConverter} that extracts user roles from the Keycloak JWT.
   * <p>
   * By default, Keycloak includes user roles inside the "roles" array within the "realm_access" claim.
   * This converter retrieves those roles and transforms them into a list of {@link SimpleGrantedAuthority}
   * instances, prefixing each role with "ROLE_" to comply with Spring Security's expectations when using
   * the {@code .hasRole()} method.
   * </p>
   * <p>
   * The method suppresses unchecked warnings because Keycloak consistently provides the "realm_access" claim
   * as a {@code Map<String, Object>} containing a {@code List<String>} for roles. Since the format is well-defined
   * and predictable in Keycloak, the unchecked cast is safe in this specific context.
   * </p>
   *
   * @return a configured instance of {@link JwtAuthenticationConverter} that maps Keycloak roles to Spring Security authorities
   */
  @Bean
  @SuppressWarnings("unchecked")
  public JwtAuthenticationConverter jwtAuthenticationConverterForKeycloak() {
    final Converter<Jwt, Collection<GrantedAuthority>> jwtGrantedAuthoritiesConverter = jwt -> {
      final Map<String, Object> realmAccess = jwt.getClaimAsMap("realm_access");
      if (realmAccess == null || realmAccess.isEmpty()) {
        return List.of();
      }
      final List<String> roles = (List<String>) realmAccess.get("roles");
      if (roles == null || roles.isEmpty()) {
        return List.of();
      }
      return roles
        .stream()
        .map(role -> "ROLE_" + role) // add prefix needed for .hasRole()
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());
    };
    var jwtAuthenticationConverter = new JwtAuthenticationConverter();
    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
    return jwtAuthenticationConverter;
  }
}
