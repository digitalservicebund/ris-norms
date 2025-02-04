package de.bund.digitalservice.ris.norms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Configuration class for defining security settings in the application. This class is annotated
 * with {@link Configuration} and {@link EnableWebSecurity} to indicate that it provides security
 * configuration for a web-based application.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private final ClientRegistrationRepository clientRegistrationRepository;

  public SecurityConfig(ClientRegistrationRepository clientRegistrationRepository) {
    this.clientRegistrationRepository = clientRegistrationRepository;
  }

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
            "/favicon.svg",
            "/actuator/health/**",
            "/actuator/prometheus"
          )
          .permitAll()
          .anyRequest()
          .authenticated()
      )
      .oauth2Login(Customizer.withDefaults())
      .logout(logout -> logout.logoutSuccessHandler(oidcLogoutSuccessHandler()))
      .csrf(AbstractHttpConfigurer::disable)
      .cors(Customizer.withDefaults())
      .sessionManagement(sessionManagement ->
        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
      )
      .exceptionHandling(exceptionHandlingConfigurer ->
        exceptionHandlingConfigurer.defaultAuthenticationEntryPointFor(
          new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED),
          new AntPathRequestMatcher("/api/**")
        )
      );
    return http.build();
  }

  private LogoutSuccessHandler oidcLogoutSuccessHandler() {
    OidcClientInitiatedLogoutSuccessHandler oidcLogoutSuccessHandler =
      new OidcClientInitiatedLogoutSuccessHandler(this.clientRegistrationRepository);

    // Sets the location that the End-User's User Agent will be redirected to
    // after the logout has been performed at the Provider
    oidcLogoutSuccessHandler.setPostLogoutRedirectUri("{baseUrl}");

    return oidcLogoutSuccessHandler;
  }
}
