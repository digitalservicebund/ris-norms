package de.bund.digitalservice.ris.norms.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * European legislation identifier
 *
 * <p>This class can be used to extract the eli from a path that includes a section like
 * "/eli/bund/{agent}/{year}/{naturalIdentifier}/{pointInTime}/{version}/{language}/{subtype}".
 */
@Getter
@Setter
@AllArgsConstructor
public class Eli {
  private String agent;
  private String year;
  private String naturalIdentifier;
  private String pointInTime;
  private String version;
  private String language;
  private String subtype;

  public String getValue() {
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
