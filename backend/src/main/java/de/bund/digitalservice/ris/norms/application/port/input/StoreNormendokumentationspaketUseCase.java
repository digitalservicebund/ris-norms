package de.bund.digitalservice.ris.norms.application.port.input;

import java.io.IOException;
import java.util.UUID;
import org.springframework.core.io.Resource;

/** UseCase for storing a newly uploaded Normendokumentationspaket for processing */
public interface StoreNormendokumentationspaketUseCase {
  /**
   * Storing a newly uploaded Normendokumentationspaket for processing
   *
   * @param options The options containing the Normendokumentationspaket and signature
   * @return the process id for getting the processing status
   */
  UUID storeNormendokumentationspaket(Options options) throws IOException;

  /**
   * A record representing the options for storing a Normendokumentationspaket.
   *
   * @param file the Normendokumentationspaket
   * @param signature the signature for verifying the Normendokumentationspaket
   */
  record Options(Resource file, Resource signature) {}
}
