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
import org.jetbrains.annotations.NotNull;

/**
 * European legislation identifier on expression level for a norm
 *
 * <p>This class can be used to extract the eli from a path that includes a section like
 * "/eli/bund/{agent}/{year}/{naturalIdentifier}/{pointInTime}/{version}/{language}".
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public final class NormExpressionEli implements NormEli, Comparable<NormExpressionEli> {

  private String agent;
  private String year;
  private String naturalIdentifier;
  private LocalDate pointInTime;
  private Integer version;
  private String language;

  @Override
  public String toString() {
    return "eli/bund/%s/%s/%s/%s/%d/%s".formatted(
        getAgent(),
        getYear(),
        getNaturalIdentifier(),
        getPointInTime().format(DateTimeFormatter.ISO_LOCAL_DATE),
        getVersion(),
        getLanguage()
      );
  }

  @Override
  public URI toUri() {
    return URI.create(toString());
  }

  /**
   * Create a {@link NormWorkEli} that contains the parts of this eli
   *
   * @return a work eli
   */
  public NormWorkEli asWorkEli() {
    return new NormWorkEli(getAgent(), getYear(), getNaturalIdentifier());
  }

  /**
   * Create an expression level eli from a string representation
   *
   * @param expressionEli the string representation of the eli
   * @return the eli
   */
  public static NormExpressionEli fromString(String expressionEli) {
    Matcher matcher = Pattern
      .compile(
        "eli/bund/(?<agent>[^/]+)/(?<year>[^/]+)/(?<naturalIdentifier>[^/]+)/(?<pointInTime>[^/]+)/(?<version>[^/]+)/(?<language>[^/]+)"
      )
      .matcher(expressionEli);

    if (!matcher.matches()) {
      throw new IllegalArgumentException("Invalid expression Eli");
    }

    return new NormExpressionEli(
      matcher.group("agent"),
      matcher.group("year"),
      matcher.group("naturalIdentifier"),
      LocalDate.parse(matcher.group("pointInTime"), DateTimeFormatter.ISO_LOCAL_DATE),
      Integer.valueOf(matcher.group("version")),
      matcher.group("language")
    );
  }

  /**
   * Create an expression level eli from an work eli and the additional data for an expression eli.
   *
   * @param workEli     the work eli to use as a base
   * @param pointInTime the date of the verkündung
   * @param version     the version of the expression (to differentiate between multiple expressions with the same pointInTime)
   * @param language    the language of the expression
   * @return the eli
   */
  public static NormExpressionEli fromWorkEli(
    NormWorkEli workEli,
    LocalDate pointInTime,
    Integer version,
    String language
  ) {
    return new NormExpressionEli(
      workEli.getAgent(),
      workEli.getYear(),
      workEli.getNaturalIdentifier(),
      pointInTime,
      version,
      language
    );
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    NormExpressionEli that = (NormExpressionEli) o;
    return Objects.equals(toString(), that.toString());
  }

  @Override
  public int hashCode() {
    return Objects.hash(toString());
  }

  @Override
  public int compareTo(@NotNull NormExpressionEli o) {
    return String.CASE_INSENSITIVE_ORDER.compare(this.toString(), o.toString());
  }
}
