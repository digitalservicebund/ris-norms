package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.Dokument;
import de.bund.digitalservice.ris.norms.domain.entity.Zeitgrenze;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentExpressionEli;
import java.util.List;

/**
 * Interface representing the use case for loading a list of {@link Zeitgrenze}.
 */
public interface LoadZeitgrenzenUseCase {
  /**
   * Retrieves a list of time boundaries from a {@link Dokument} based on the provided query.
   *
   * @param query The query containing the ELI (European Legislation Identifier) of the {@link Dokument}.
   * @return the list of {@link Zeitgrenze}
   */
  List<Zeitgrenze> loadZeitgrenzenFromDokument(Query query);

  /**
   * A record representing the parameters needed to query time boundaries related to a {@link Dokument}.
   *
   * @param eli The ELI used to identify the {@link Dokument} in the query.
   */
  record Query(DokumentExpressionEli eli) {}
}
