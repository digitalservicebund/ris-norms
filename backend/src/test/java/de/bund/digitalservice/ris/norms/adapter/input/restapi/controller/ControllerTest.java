package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.OAuth2ResourceServerAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.core.annotation.AliasFor;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@WebMvcTest(
  excludeAutoConfiguration = {
    SecurityAutoConfiguration.class, OAuth2ResourceServerAutoConfiguration.class,
  }
)
public @interface ControllerTest {
  @AliasFor(annotation = WebMvcTest.class, attribute = "controllers")
  Class<?> value();
}
