package de.bund.digitalservice.ris.norms.utils;

import org.jetbrains.annotations.NotNull;

/**
 * Builds an ELI out of its components, cf. p136 of theLDML_de 1.6 specs at <a
 * href="https://github.com/digitalservicebund/ris-norms/commit/17778285381a674f1a2b742ed573b7d3d542ea24">...</a>)
 */
public class EliBuilder {
  @NotNull
  public static String buildEli(
      String agent,
      String year,
      String naturalIdentifier,
      String pointInTime,
      String version,
      String language,
      String subtype) {
    return "eli/bund/"
        + agent
        + "/"
        + year
        + "/"
        + naturalIdentifier
        + "/"
        + pointInTime
        + "/"
        + version
        + "/"
        + language
        + "/"
        + subtype;
  }
}
