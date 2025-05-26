package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.Zeitgrenze;
import de.bund.digitalservice.ris.norms.domain.entity.ZielnormReference;
import de.bund.digitalservice.ris.norms.domain.entity.eid.EId;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormWorkEli;
import java.util.List;

/** Use case for updating the zielnorm references of a norm. */
public interface UpdateZielnormReferencesUseCase {
  /**
   * Updates zielnorm references
   *
   * @param options Options used for identifying the norm and the updated zielnorm-references
   * @return All zielnorm references of the norm
   */
  List<ZielnormReference> updateZielnormReferences(Options options);

  /**
   * Contains the parameters needed for loading the zielnorm references from a norm.
   *
   * @param eli The ELI used to identify the norm
   * @param zielnormReferences a list of zielnorm references. References with the same eId as an existing one overwrite those, references with a new eId are created. All other references remain unchanged.
   */
  record Options(NormExpressionEli eli, List<ZielnormReferenceUpdateData> zielnormReferences) {}

  /**
   * Data for updating a zielnorm reference
   * @param typ the new typ
   * @param geltungszeit the new id of the geltungszeitregel
   * @param eId the eId of the element referencing the Änderungsbefehl. If a {@link ZielnormReference} for this eId already exists it is updated, otherwise a new reference will be created.
   * @param zielnorm the ELI of the zielnorm targeted by the Änderungsbefehl
   */
  record ZielnormReferenceUpdateData(
    String typ,
    Zeitgrenze.Id geltungszeit,
    EId eId,
    NormWorkEli zielnorm
  ) {}
}
