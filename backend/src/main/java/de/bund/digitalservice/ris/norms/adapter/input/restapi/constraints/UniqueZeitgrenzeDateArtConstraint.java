package de.bund.digitalservice.ris.norms.adapter.input.restapi.constraints;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/** Provides the {@link UniqueZeitgrenzeDateArtConstraintValidator} for the zeitgrenzen */
@Constraint(validatedBy = UniqueZeitgrenzeDateArtConstraintValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueZeitgrenzeDateArtConstraint {
  /**
   * @return the validation message
   */
  String message() default "Not all combinations of date + art are unique.";

  /**
   * @return default groups
   */
  Class<?>[] groups() default {};

  /**
   * @return default payload
   */
  Class<? extends Payload>[] payload() default {};
}
