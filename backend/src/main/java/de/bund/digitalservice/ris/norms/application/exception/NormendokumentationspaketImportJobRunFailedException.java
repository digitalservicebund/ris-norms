package de.bund.digitalservice.ris.norms.application.exception;

import de.bund.digitalservice.ris.norms.utils.exceptions.NormsAppException;
import java.net.URI;
import java.util.Map;

/**
 * Exception indicating the Normendokumentationspaket import job failed
 */
public class NormendokumentationspaketImportJobRunFailedException
  extends RuntimeException
  implements NormsAppException {

  public NormendokumentationspaketImportJobRunFailedException() {
    super("Tried to import a Normendokumentationspacket the max amount of times but failed");
  }

  @Override
  public URI getType() {
    return URI.create("/errors/normendokumentationspaket-import-failed/job-run-failed");
  }

  @Override
  public String getTitle() {
    return "Job Run Failed";
  }

  @Override
  public Map<String, Object> getProperties() {
    return Map.of();
  }
}
