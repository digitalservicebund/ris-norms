package de.bund.digitalservice.template;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

  @Generated
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
