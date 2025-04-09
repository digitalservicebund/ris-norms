package de.bund.digitalservice.ris.norms.adapter.input.restapi.constraints;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.ZeitgrenzeRequestSchema;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.stream.Collectors;

/** Validates if there are no duplicates in the combination of the "date" and the "art" */
public class UniqueZeitgrenzeDateArtConstraintValidator
  implements ConstraintValidator<UniqueZeitgrenzeDateArtConstraint, List<ZeitgrenzeRequestSchema>> {

  @Override
  public boolean isValid(
    List<ZeitgrenzeRequestSchema> zeitgrenzen,
    ConstraintValidatorContext context
  ) {
    return (
      zeitgrenzen
        .stream()
        .map(zeitgrenze -> zeitgrenze.getDate() + "_" + zeitgrenze.getArt())
        .collect(Collectors.toSet())
        .size() ==
      zeitgrenzen.size()
    );
  }
}
