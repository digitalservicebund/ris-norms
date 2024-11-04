package de.bund.digitalservice.ris.norms.utils.exceptions;

import lombok.Getter;

/**
 * Abstract exception when communicating with the external storage. Does not need to implement {@link NormsAppException}
 * because this exception will not be originated after an API call and does not need to be translated into a response error for the frontend.
 */
@Getter
public abstract class StorageException extends RuntimeException {

  protected StorageException(final String message, final Exception e) {
    super(message, e);
  }
}
