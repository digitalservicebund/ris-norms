package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.Dokument;
import de.bund.digitalservice.ris.norms.domain.entity.Proprietary;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentExpressionEli;

/** Use case for getting {@link Proprietary} metadata from a {@link Dokument}. */
public interface LoadProprietaryFromDokumentUseCase {
  /**
   * Retrieves the {@link Proprietary} metadata from a {@link Dokument}.
   *
   * @param query Query used for identifying the {@link Dokument}.
   * @return Proprietary metadata of the {@link Dokument}, if it has any.
   */
  Proprietary loadProprietaryFromDokument(Query query);

  /**
   * Query with the expression eli of the {@link Dokument}
   *
   * @param dokumentExpressionEli The ELI used to identify the {@link Dokument} at the expression level
   */
  record Query(DokumentExpressionEli dokumentExpressionEli) {}
}
