package de.bund.digitalservice.ris.norms.adapter.input.restapi.constraints;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.TimeBoundarySchema;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.List;

/** Validates if TimeBoundary dates are unique */
public class UniqueTimeBoundariesDatesConstraintValidator
    implements ConstraintValidator<UniqueTimeBoundariesDatesConstraint, List<TimeBoundarySchema>> {
  @Override
  public boolean isValid(
      List<TimeBoundarySchema> timeBoundaries, ConstraintValidatorContext context) {
    return new HashSet<>(timeBoundaries.stream().map(TimeBoundarySchema::getDate).toList()).size()
        == timeBoundaries.size();
  }
}
