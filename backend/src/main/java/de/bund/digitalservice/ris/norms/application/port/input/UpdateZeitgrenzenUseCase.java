package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.Dokument;
import de.bund.digitalservice.ris.norms.domain.entity.Zeitgrenze;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentExpressionEli;
import java.util.List;

/**
 * Interface representing the use case for updating a list of {@link Zeitgrenze}.
 */
public interface UpdateZeitgrenzenUseCase {
  /**
   * Updates a list of time boundaries of a {@link Dokument} based on the provided query.
   *
   * @param query The query containing the ELI (European Legislation Identifier) of the {@link Dokument}.
   * @return the list of {@link Zeitgrenze}
   */
  List<Zeitgrenze> updateZeitgrenzenOfDokument(Query query);

  /**
   * A record representing the parameters needed to query time boundaries related to a {@link Dokument}.
   *
   * @param eli The ELI used to identify the {@link Dokument} in the query.
   * @param zeitgrenzen the list of the new {@link Zeitgrenze}
   */
  record Query(DokumentExpressionEli eli, List<Zeitgrenze> zeitgrenzen) {}
}
