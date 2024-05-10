package de.bund.digitalservice.ris.norms.adapter.input.restapi.constraints;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/** Provides a UniqueTimeBoundariesDatesConstraint for TimeBoundary Date uniqueness validation */
@Constraint(validatedBy = UniqueTimeBoundariesDatesConstraintValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueTimeBoundariesDatesConstraint {
  /**
   * @return the validation message
   */
  String message() default "All dates must be unique.";

  /**
   * @return default groups
   */
  Class<?>[] groups() default {};

  /**
   * @return default payload
   */
  Class<? extends Payload>[] payload() default {};
}
