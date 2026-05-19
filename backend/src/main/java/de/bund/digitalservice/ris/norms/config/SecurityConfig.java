package de.bund.digitalservice.ris.norms.config;

import de.bund.digitalservice.ris.norms.domain.entity.Roles;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;

/**
 * Configuration class for defining security settings in the application. This class is annotated
 * with {@link Configuration} and {@link EnableWebSecurity} to indicate that it provides security
 * configuration for a web-based application.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Value("${csp.report-uri}")
  private String cspReportUri;

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
          // Restrict /api/v1/external/** to e-V user
          .requestMatchers("/api/v1/external/**")
          .hasRole(Roles.EVERKUENDUNG_USER)
          // Allow /api/v1/fonto/** for everyone (just for trying out the integration, do not do this in prod)
          .requestMatchers("/api/v1/fonto/**")
          .permitAll()
          // Restrict the rest of /api/v1/** to norms user, excluding /api/v1/external/** because matched before
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
      )
      .headers(httpSecurityHeadersConfigurer ->
        httpSecurityHeadersConfigurer
          .contentSecurityPolicy(contentSecurityPolicyConfig ->
            contentSecurityPolicyConfig.policyDirectives(
              """
              default-src 'self';
              img-src 'self' data:;
              style-src 'self' 'unsafe-inline';
              script-src 'self' 'unsafe-eval';
              connect-src 'self' *.sentry.io neuris.login.bare.id data:;
              report-uri %s;
              report-to csp-endpoint
              """.formatted(cspReportUri)
                .replaceAll("[\\n\\r]+", " ")
            )
          )
          .addHeaderWriter(
            new StaticHeadersWriter(
              "Report-To",
              "{\"group\":\"csp-endpoint\",\"max_age\":10886400,\"endpoints\":[{\"url\":\"%s\"}],\"include_subdomains\":true}".formatted(
                cspReportUri
              )
            )
          )
          .addHeaderWriter(
            new StaticHeadersWriter(
              "Reporting-Endpoints",
              "csp-endpoint=\"%s\"".formatted(cspReportUri)
            )
          )
          .contentTypeOptions(contentTypeOptionsConfig -> {})
          .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
          .referrerPolicy(referrerPolicyConfig ->
            referrerPolicyConfig.policy(
              ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN
            )
          )
          .permissionsPolicyHeader(permissionsPolicyConfig ->
            permissionsPolicyConfig.policy(
              "accelerometer=(), ambient-light-sensor=(), autoplay=(), battery=(), camera=(), cross-origin-isolated=(), " +
                "display-capture=(), document-domain=(), encrypted-media=(), execution-while-not-rendered=(), " +
                "execution-while-out-of-viewport=(), fullscreen=(), geolocation=(), gyroscope=(), keyboard-map=(), " +
                "magnetometer=(), microphone=(), midi=(), navigation-override=(), payment=(), picture-in-picture=(), " +
                "publickey-credentials-get=(), screen-wake-lock=(), sync-xhr=(), usb=(), web-share=(), xr-spatial-tracking=(), " +
                "clipboard-read=(self), clipboard-write=(self), gamepad=(), speaker-selection=(), conversion-measurement=(), " +
                "focus-without-user-activation=(self), hid=(), idle-detection=(), interest-cohort=(), serial=(), sync-script=(), " +
                "trust-token-redemption=(), window-placement=(), vertical-scroll=(self)"
            )
          )
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
