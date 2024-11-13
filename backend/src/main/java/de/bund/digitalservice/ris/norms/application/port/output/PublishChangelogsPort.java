package de.bund.digitalservice.ris.norms.application.port.output;

/**
 * Interface representing the output port for publishing changelogs to public and private storages
 */
public interface PublishChangelogsPort {
  /**
   * Publishes the changelogs to designated public and private locations.
   */
  void publishChangelogs();
}
