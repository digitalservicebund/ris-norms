package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.Regelungstext;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentExpressionEli;
import java.util.Optional;

/**
 * Interface representing the use case for updating the XML representation of a regelungstext.
 */
public interface UpdateRegelungstextXmlUseCase {
  /**
   * Updates a xml representation of {@link Regelungstext} based on the provided options.
   *
   * @param options The options specifying the Dokument to be loaded.
   * @return An {@link Optional} containing the saved xml representation of {@link Regelungstext} if found,
   *     or empty if not found.
   */
  String updateRegelungstextXml(Options options);

  /**
   * A record representing the options for updating the XML representation of a dokument.
   *
   * @param eli The ELI (European Legislation Identifier) used to identify the dokument.
   * @param xml The new XML representation of the Dokument.
   */
  record Options(DokumentExpressionEli eli, String xml) {}
}
