package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.validator.AmendingLawValidator;
import de.bund.digitalservice.ris.norms.application.validator.SingleModValidator;
import de.bund.digitalservice.ris.norms.application.validator.Validator;
import de.bund.digitalservice.ris.norms.application.validator.ValidatorName;
import de.bund.digitalservice.ris.norms.utils.exceptions.ValidationException;
import java.util.EnumMap;
import java.util.Map;
import org.springframework.stereotype.Service;

/** Service for managing which implementation of the {@link Validator} interface will be call. */
@Service
public class ValidationService {

  private final Map<ValidatorName, Validator> validators = new EnumMap<>(ValidatorName.class);

  public ValidationService(
      final SingleModValidator singleModValidator,
      final AmendingLawValidator amendingLawValidator) {
    validators.put(SingleModValidator.NAME, singleModValidator);
    validators.put(AmendingLawValidator.NAME, amendingLawValidator);
  }

  /**
   * Activates the validation of the specific custom validator with the given data.a
   *
   * @param validatorName - the custom validator name
   * @param args - variable size of input parameters
   * @throws ValidationException - whenever a validation fails
   */
  public void validate(final ValidatorName validatorName, Object... args)
      throws ValidationException {
    validators.get(validatorName).validate(args);
  }
}
