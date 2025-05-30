package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.ZielnormReference;
import de.bund.digitalservice.ris.norms.domain.entity.eid.EId;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import java.util.List;

/** Use case for deleting zielnorm references of a norm. */
public interface DeleteZielnormReferencesUseCase {
  /**
   * Deletes zielnorm references
   *
   * @param options Options used for identifying the norm and the zielnorm-references to be deleted
   * @return All (remaining) zielnorm references of the norm
   */
  List<ZielnormReference> deleteZielnormReferences(Options options);

  /**
   * Contains the parameters needed for deleting zielnorm references from a norm.
   *
   * @param eli The ELI used to identify the norm
   * @param zielnormReferenceEIds List of eIds of Änderungsbefehle whose references should be deleted
   */
  record Options(NormExpressionEli eli, List<EId> zielnormReferenceEIds) {}
}
