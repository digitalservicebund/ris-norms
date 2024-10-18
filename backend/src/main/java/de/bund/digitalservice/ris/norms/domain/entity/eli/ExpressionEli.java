package de.bund.digitalservice.ris.norms.domain.entity.eli;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * European legislation identifier on expression level
 *
 * <p>This class can be used to extract the eli from a path that includes a section like
 * "/eli/bund/{agent}/{year}/{naturalIdentifier}/{pointInTime}/{version}/{language}/{subtype}".
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public final class ExpressionEli implements Eli {

  private String agent;
  private String year;
  private String naturalIdentifier;
  private LocalDate pointInTime;
  private Integer version;
  private String language;
  private String subtype;

  @Override
  public String toString() {
    return "eli/bund/%s/%s/%s/%s/%d/%s/%s".formatted(
        getAgent(),
        getYear(),
        getNaturalIdentifier(),
        getPointInTime().format(DateTimeFormatter.ISO_LOCAL_DATE),
        getVersion(),
        getLanguage(),
        getSubtype()
      );
  }

  /**
   * Create the URI for the eli to be used in e.g. href attributes.
   * <p>The URI does not contain the subtype
   *
   * @return the URI for the eli
   */
  @Override
  public URI toUri() {
    return URI.create(
        "eli/bund/%s/%s/%s/%s/%d/%s".formatted(
          getAgent(),
          getYear(),
          getNaturalIdentifier(),
            getPointInTime().format(DateTimeFormatter.ISO_LOCAL_DATE),
          getVersion(),
          getLanguage()
        )
    );
  }

  /**
   * Create a {@link WorkEli} that contains the parts of this eli
   *
   * @return a work eli
   */
  public WorkEli asWorkEli() {
    return new WorkEli(getAgent(), getYear(), getNaturalIdentifier(), getSubtype());
  }

  /**
   * Create an expression level eli from a string representation
   *
   * @param expressionEli the string representation of the eli
   * @return the eli
   */
  public static ExpressionEli fromString(String expressionEli) {
    Matcher matcher = Pattern
      .compile(
        "eli/bund/(?<agent>[^/]+)/(?<year>[^/]+)/(?<naturalIdentifier>[^/]+)/(?<pointInTime>[^/]+)/(?<version>[^/]+)/(?<language>[^/]+)/(?<subtype>[^/.]+)"
      )
      .matcher(expressionEli);

    if (!matcher.matches()) {
      throw new IllegalArgumentException("Invalid expression Eli");
    }

    return new ExpressionEli(
      matcher.group("agent"),
      matcher.group("year"),
      matcher.group("naturalIdentifier"),
        LocalDate.parse(matcher.group("pointInTime"), DateTimeFormatter.ISO_LOCAL_DATE),
        Integer.valueOf(matcher.group("version")),
      matcher.group("language"),
      matcher.group("subtype")
    );
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ExpressionEli that = (ExpressionEli) o;
    return Objects.equals(toString(), that.toString());
  }

  @Override
  public int hashCode() {
    return Objects.hash(toString());
  }
}
