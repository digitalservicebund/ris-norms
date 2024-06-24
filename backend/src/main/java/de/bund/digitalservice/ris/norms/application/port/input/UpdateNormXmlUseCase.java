package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.common.exception.NormNotFoundException;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;

/**
 * Interface representing the use case for updating the XML representation of a norm.
 * Implementations of this interface should provide functionality to update the XML based on a given
 * query.
 */
public interface UpdateNormXmlUseCase {

  /**
   * Updates a xml representation of {@link Norm} based on the provided query.
   *
   * @param query The query specifying the amending law to be loaded.
   * @return the saved xml representation of {@link Norm}.
   */
  String updateNormXml(Query query) throws InvalidUpdateException, NormNotFoundException;

  /**
   * A record representing the query for updating the XML representation of a norm. The query
   * includes the ELI (European Legislation Identifier) to identify the norm.
   *
   * @param eli The ELI (European Legislation Identifier) used to identify the norm in the query.
   * @param xml The new XML representation of the norm.
   */
  record Query(String eli, String xml) {}

  /** This exception indicates that a proposed change to a norm is not allowed */
  class InvalidUpdateException extends Exception {
    public InvalidUpdateException(String message) {
      super(message);
    }
  }
}
