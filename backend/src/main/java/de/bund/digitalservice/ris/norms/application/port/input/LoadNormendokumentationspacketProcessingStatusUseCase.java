package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.VerkuendungImportProcess;
import de.bund.digitalservice.ris.norms.domain.entity.VerkuendungImportProcessDetail;
import java.util.List;
import java.util.UUID;

/**
 * Interface representing the use case for loading the process status of importing a Normendokumentationspaket.
 *
 */
public interface LoadNormendokumentationspacketProcessingStatusUseCase {
  /**
   * Loads the status of the import of a Normendokumentationspaket
   *
   * @param query for loading the status
   * @return the status as {@link VerkuendungImportProcess} of the import process with a {@link List} of optional errors as {@link VerkuendungImportProcessDetail}s that occurred during the import
   */
  VerkuendungImportProcess getStatus(Query query);

  /**
   * The query forloading the process status of importing a Normendokumentationspaket.
   *
   * @param processingId that is needed to identify the job
   */
  record Query(UUID processingId) {}
}
