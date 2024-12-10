package de.bund.digitalservice.ris.norms.application.exception;

import de.bund.digitalservice.ris.norms.utils.exceptions.NormsAppException;
import lombok.Getter;

/**
 * Custom exception for when the migration log did not contain any migrated norms. Does not need to implement {@link NormsAppException}
 * because this exception will not be originated after an API call and does not need to be translated into a response error for the frontend.
 */
@Getter
public class MigrationJobException extends RuntimeException {

  public MigrationJobException() {
    super("Migration job migrated did not migrate any norm");
  }
}
