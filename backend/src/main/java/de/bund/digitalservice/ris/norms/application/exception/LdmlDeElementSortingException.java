package de.bund.digitalservice.ris.norms.application.exception;

import de.bund.digitalservice.ris.norms.application.service.LdmlDeElementSorter;
import de.bund.digitalservice.ris.norms.utils.exceptions.NormsAppException;

/**
 * Custom exception for when the {@link LdmlDeElementSorter} encounters a problem.
 * Does not implement {@link NormsAppException} as it is not impacted by the user provided data. It instead shows that
 * we have a problem with the implementation of the {@link LdmlDeElementSorter} for the current LDML.de xsd-schema.
 */
public class LdmlDeElementSortingException extends RuntimeException {

  public LdmlDeElementSortingException(String message) {
    super(message);
  }
}
