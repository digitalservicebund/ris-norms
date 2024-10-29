package de.bund.digitalservice.ris.norms.utils.exceptions;

import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import lombok.Getter;

/**
 * Abstract exception when a {@link Norm} entity fails to publish. Does not need to implement {@link NormsAppException}
 * because this exception will not be originated after an API call and does not need to be translated into a response error for the frontend.
 */
@Getter
public abstract class PublishException extends RuntimeException {

  protected PublishException(final String message, final Exception e) {
    super(message, e);
  }
}
