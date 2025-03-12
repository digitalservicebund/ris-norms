package de.bund.digitalservice.ris.norms.application.port.output;

import java.util.List;

/**
 * Port for loading a list of amtliche abkürzungen of norms that are allowed to be published to the portal prototype.
 */
public interface LoadPortalPublishingAllowListPort {
  /**
   * Load a list of amtliche abkürzungen of norms that are allowed to be published to the portal prototype.
   * @return list of allowed norm abkürzungen
   */
  List<String> loadPortalPublishingAllowListPort();
}
