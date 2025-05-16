package de.bund.digitalservice.ris.norms.config;

import javax.sql.DataSource;
import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.jdbctemplate.JdbcTemplateLockProvider;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Configuration class for defining scheduler lock settings in the application. This class is
 * annotated with {@link Configuration}, {@link EnableSchedulerLock} and {@link EnableScheduling} to
 * activate the scheduler lock
 */
@Configuration
@EnableScheduling
@EnableSchedulerLock(defaultLockAtMostFor = "180m")
public class SchedulerLockConfig {

  /**
   * Configuration to enable Spring dependency injection to create and handle a {@link LockProvider}
   *
   * @param dataSource where the lock is saved to
   * @return a {@link LockProvider}
   */
  @Bean
  public LockProvider lockProvider(DataSource dataSource) {
    return new JdbcTemplateLockProvider(
      JdbcTemplateLockProvider.Configuration.builder()
        .withJdbcTemplate(new JdbcTemplate(dataSource))
        .usingDbTime()
        .build()
    );
  }
}
