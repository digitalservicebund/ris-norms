package de.bund.digitalservice.ris.norms.domain.entity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
public class Eli {
  private String agent;
  private String year;
  private String naturalIdentifier;
  private String pointInTime;
  private String version;
  private String language;
  private String subtype;

  public Eli(String eli) {
    Matcher matcher =
        Pattern.compile(
                "eli/bund/(?<agent>[^/]+)/(?<year>[^/]+)/(?<naturalIdentifier>[^/]+)/(?<pointInTime>[^/]+)/(?<version>[^/]+)/(?<language>[^/]+)/(?<subtype>[^/.]+)(\\.xml)?")
            .matcher(eli);

    if (!matcher.matches()) {
      throw new IllegalArgumentException("Invalid Eli");
    }

    this.agent = matcher.group("agent");
    this.year = matcher.group("year");
    this.naturalIdentifier = matcher.group("naturalIdentifier");
    this.pointInTime = matcher.group("pointInTime");
    this.version = matcher.group("version");
    this.language = matcher.group("language");
    this.subtype = matcher.group("subtype");
  }

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
