package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.Regelungstext;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentExpressionEli;
import java.util.Optional;

/**
 * Interface representing the use case for updating the XML representation of a regelungstext.
 */
public interface UpdateRegelungstextXmlUseCase {
  /**
   * Updates a xml representation of {@link Regelungstext} based on the provided query.
   *
   * @param query The query specifying the Dokument to be loaded.
   * @return An {@link Optional} containing the saved xml representation of {@link Regelungstext} if found,
   *     or empty if not found.
   */
  String updateRegelungstextXml(Query query);

  /**
   * A record representing the query for updating the XML representation of a dokument.
   *
   * @param eli The ELI (European Legislation Identifier) used to identify the dokument in the query.
   * @param xml The new XML representation of the Dokument.
   */
  record Query(DokumentExpressionEli eli, String xml) {}
}
