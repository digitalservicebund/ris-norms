package de.bund.digitalservice.ris.norms.application.port.input;

import org.springframework.web.multipart.MultipartFile;

/** UseCase for storing a newly uploaded Normendokumentationspaket for processing */
public interface StoreNormendokumentationspaketUseCase {
  /**
   * Storing a newly uploaded Normendokumentationspaket for processing
   *
   * @param query The query containing the Normendokumentationspaket and signature
   * @return the process id for getting the processing status
   */
  String storeNormendokumentationspaket(StoreNormendokumentationspaketUseCase.Query query);

  /**
   * A record representing the query for storing a Normendokumentationspaket.
   *
   * @param file the Normendokumentationspaket
   * @param signature the signature for verifying the Normendokumentationspaket
   */
  record Query(MultipartFile file, MultipartFile signature) {}
}
