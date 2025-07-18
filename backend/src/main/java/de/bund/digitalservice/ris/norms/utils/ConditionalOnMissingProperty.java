package de.bund.digitalservice.ris.norms.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.AliasFor;

/**
 * Applies the configuration only when the property is missing (or has the value false).
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
@ConditionalOnProperty(matchIfMissing = true, havingValue = "false")
public @interface ConditionalOnMissingProperty {
  /**
   * The name of the property. See {@link ConditionalOnProperty#name()}
   * @return the name of the property
   */
  @AliasFor(annotation = ConditionalOnProperty.class, attribute = "value")
  String[] value() default {};
}
