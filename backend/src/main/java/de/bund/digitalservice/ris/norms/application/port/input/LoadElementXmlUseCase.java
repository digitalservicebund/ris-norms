package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.EId;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentExpressionEli;

/** Use case for getting a single element as an XML string from a Dokument. */
public interface LoadElementXmlUseCase {
  /**
   * Retrieves an element inside a Dokument and returns its XML.
   *
   * @param query Query used for identifying the element
   * @return The XML string
   */
  String loadElementXml(Query query);

  /**
   * Contains the parameters needed for loading an element from a Dokument.
   *
   * @param eli The ELI used to identify the Dokument
   * @param eid The eId of the element inside the Dokument
   */
  record Query(DokumentExpressionEli eli, EId eid) {}
}
