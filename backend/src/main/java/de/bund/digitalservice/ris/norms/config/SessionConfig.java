package de.bund.digitalservice.ris.norms.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * Configuration class for defining session settings in the application. This class is annotated
 * with {@link Configuration} and {@link EnableRedisHttpSession} to indicate that it provides
 * session configuration. Separate config class so that controller unit tests don't need a redis.
 */
@Configuration
@EnableRedisHttpSession
public class SessionConfig {}
