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
 * European legislation identifier on expression level for a dokument of a norm
 *
 * <p>This class can be used to extract the eli from a path that includes a section like
 * "/eli/bund/{agent}/{year}/{naturalIdentifier}/{pointInTime}/{version}/{language}/{subtype}".
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public final class DokumentExpressionEli implements DokumentEli, Comparable<DokumentExpressionEli> {

  private String agent;
  private String year;
  private String naturalIdentifier;
  private LocalDate pointInTime;
  private Integer version;
  private String language;
  private String subtype;

  /**
   * Create an expression level eli from a string representation
   *
   * @param expressionEli the string representation of the eli
   * @return the eli
   */
  public static DokumentExpressionEli fromString(String expressionEli) {
    Matcher matcher = Pattern
      .compile(
        "eli/bund/(?<agent>[^/]+)/(?<year>[^/]+)/(?<naturalIdentifier>[^/]+)/(?<pointInTime>[^/]+)/(?<version>[^/]+)/(?<language>[^/]+)/(?<subtype>[^/.]+)"
      )
      .matcher(expressionEli);

    if (!matcher.matches()) {
      throw new IllegalArgumentException("Invalid expression Eli");
    }

    return new DokumentExpressionEli(
      matcher.group("agent"),
      matcher.group("year"),
      matcher.group("naturalIdentifier"),
      LocalDate.parse(matcher.group("pointInTime"), DateTimeFormatter.ISO_LOCAL_DATE),
      Integer.valueOf(matcher.group("version")),
      matcher.group("language"),
      matcher.group("subtype")
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
  public static DokumentExpressionEli fromWorkEli(
    DokumentWorkEli workEli,
    LocalDate pointInTime,
    Integer version,
    String language
  ) {
    return new DokumentExpressionEli(
      workEli.getAgent(),
      workEli.getYear(),
      workEli.getNaturalIdentifier(),
      pointInTime,
      version,
      language,
      workEli.getSubtype()
    );
  }

  /**
   * Create an eli for a Dokument from the eli for a norm.
   * @param normEli the eli for the norm of the Dokument
   * @param subtype the subtype of the Dokument
   * @return the eli for the document
   */
  public static DokumentExpressionEli fromNormEli(NormExpressionEli normEli, String subtype) {
    return new DokumentExpressionEli(
      normEli.getAgent(),
      normEli.getYear(),
      normEli.getNaturalIdentifier(),
      normEli.getPointInTime(),
      normEli.getVersion(),
      normEli.getLanguage(),
      subtype
    );
  }

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
   * Create a {@link DokumentWorkEli} that contains the parts of this eli
   *
   * @return a work eli
   */
  public DokumentWorkEli asWorkEli() {
    return new DokumentWorkEli(getAgent(), getYear(), getNaturalIdentifier(), getSubtype());
  }

  /**
   * Create a {@link NormExpressionEli} that contains the parts of this eli
   *
   * @return a norm eli
   */
  @Override
  public NormExpressionEli asNormEli() {
    return new NormExpressionEli(
      getAgent(),
      getYear(),
      getNaturalIdentifier(),
      getPointInTime(),
      getVersion(),
      getLanguage()
    );
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    DokumentExpressionEli that = (DokumentExpressionEli) o;
    return Objects.equals(toString(), that.toString());
  }

  @Override
  public int hashCode() {
    return Objects.hash(toString());
  }

  @Override
  public int compareTo(@NotNull DokumentExpressionEli o) {
    return String.CASE_INSENSITIVE_ORDER.compare(this.toString(), o.toString());
  }
}
