package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.Regelungstext;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentEli;
import java.util.Optional;

/**
 * Interface representing the use case for loading the xml representation of a {@link Regelungstext}.
 */
public interface LoadRegelungstextXmlUseCase {
  /**
   * Retrieves the xml representation of a dokument based on the provided query.
   *
   * @param query The query containing the ELI (European Legislation Identifier) of the dokument.
   * @return An {@link Optional} containing the loaded {@link Regelungstext} if found, or empty if not found.
   */
  String loadRegelungstextXml(Query query);

  /**
   * A record representing the query for loading the xml representation of a dokument.
   *
   * @param eli The ELI (European Legislation Identifier) used to identify the dokument in the query.
   */
  record Query(DokumentEli eli) {}
}
