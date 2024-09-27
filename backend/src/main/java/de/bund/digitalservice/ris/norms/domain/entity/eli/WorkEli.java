package de.bund.digitalservice.ris.norms.domain.entity.eli;

import java.net.URI;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * European legislation identifier on work level
 *
 * <p>This class can be used to extract the eli from a path that includes a section like
 * "/eli/bund/{agent}/{year}/{naturalIdentifier}/{subtype}".
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkEli {

  private String agent;
  private String year;
  private String naturalIdentifier;
  private String subtype;

  /**
   * Create a work level eli from a string representation
   *
   * @param workEli the string representation of the eli
   * @return the eli
   */
  public static WorkEli fromString(String workEli) {
    Matcher matcher = Pattern
      .compile(
        "eli/bund/(?<agent>[^/]+)/(?<year>[^/]+)/(?<naturalIdentifier>[^/]+)/(?<subtype>[^/]+)"
      )
      .matcher(workEli);

    if (!matcher.matches()) {
      throw new IllegalArgumentException("Invalid work Eli");
    }

    return new WorkEli(
      matcher.group("agent"),
      matcher.group("year"),
      matcher.group("naturalIdentifier"),
      matcher.group("subtype")
    );
  }

  @Override
  public String toString() {
    return "eli/bund/%s/%s/%s/%s".formatted(
        getAgent(),
        getYear(),
        getNaturalIdentifier(),
        getSubtype()
      );
  }

  /**
   * Create the URI for the eli to be used in e.g. href attributes.
   * <p>The URI does not contain the subtype
   *
   * @return the URI for the eli
   */
  public URI toUri() {
    return URI.create("eli/bund/%s/%s/%s".formatted(getAgent(), getYear(), getNaturalIdentifier()));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    WorkEli that = (WorkEli) o;
    return Objects.equals(toString(), that.toString());
  }

  @Override
  public int hashCode() {
    return Objects.hash(toString());
  }
}
