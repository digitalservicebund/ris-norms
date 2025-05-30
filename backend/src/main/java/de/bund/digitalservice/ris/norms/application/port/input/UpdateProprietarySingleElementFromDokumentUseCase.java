package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.Dokument;
import de.bund.digitalservice.ris.norms.domain.entity.Proprietary;
import de.bund.digitalservice.ris.norms.domain.entity.eid.EId;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentExpressionEli;

/**
 * Use case for updating metadata for a single element within the {@link Proprietary} node of a
 * {@link Dokument}.
 */
public interface UpdateProprietarySingleElementFromDokumentUseCase {
  /**
   * Updates specific metadata for a single element from a {@link Dokument}.
   *
   * @param options Options used for identifying the {@link Dokument} as well as the eId of the single element and the metadata themselves.
   * @return Proprietary node of the norm with the updated metadata.
   */
  Proprietary updateProprietarySingleElementFromDokument(Options options);

  /**
   * Contains the parameters needed for loading proprietary metadata from a norm.
   *
   * @param dokumentExpressionEli The ELI used to identify the {@link Dokument} at the expression level
   * @param eid the eId of the single element within the {@link Dokument}
   * @param inputMetadata object containing the metadata to update
   */
  record Options(
    DokumentExpressionEli dokumentExpressionEli,
    EId eid,
    InputMetadata inputMetadata
  ) {}

  /**
   * Record representing the list of metadata to update.
   *
   * @param artDerNorm - "Art der Norm" SN or ÄN or ÜN
   */
  record InputMetadata(String artDerNorm) {}
}
