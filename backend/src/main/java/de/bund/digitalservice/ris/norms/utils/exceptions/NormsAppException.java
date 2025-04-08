package de.bund.digitalservice.ris.norms.utils.exceptions;

import java.net.URI;
import java.util.Map;

/**
 * Marker interface for all custom exceptions thrown in the norms app
 * See also RFC-9457, ADR-0011 and ADR-0012
 * */
public interface NormsAppException {
  /**
   * Detail message about the exception, written in english
   *
   * @return message
   */
  String getMessage();

  /**
   * The type of exception starting with `/error/`
   * @return the type of the exception
   */
  URI getType();

  /**
   * The title describing the exception, written in english
   * @return the title of the exception
   */
  String getTitle();

  /**
   * Additional details about the exception that could be sent to the user.
   * See "Extension Members" in ADR-0012
   *
   * @return additional details about the exception
   */
  Map<String, Object> getProperties();
}
