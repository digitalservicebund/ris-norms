package de.bund.digitalservice.ris.norms.application.validator;

import de.bund.digitalservice.ris.norms.utils.exceptions.ValidationException;

/** Interface to be implemented by any custom validator. */
public interface Validator {

  /**
   * Validates the passed data within the custom implementation.
   *
   * @param args - variable-sized input parameters.
   * @throws ValidationException whenever a validation step fails
   */
  void validate(Object... args) throws ValidationException;
}
