package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.eid.EId;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentExpressionEli;

/** Use case for getting a single element as an HTML string from a dokument. */
public interface LoadElementHtmlUseCase {
  /**
   * Retrieves an element inside a dokument and renders its HTML preview.
   *
   * @param options Options used for identifying the element
   * @return The HTML string
   */
  String loadElementHtml(Options options);

  /**
   * Contains the parameters needed for loading an element from a dokument.
   *
   * @param eli The ELI used to identify the dokument
   * @param eid The eId of the element inside the dokument
   */
  record Options(DokumentExpressionEli eli, EId eid) {}
}
