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
 * European legislation identifier on manifestation level
 *
 * <p>This class can be used to extract the eli from a path that includes a section like
 * "/eli/bund/{agent}/{year}/{naturalIdentifier}/{pointInTime}/{version}/{language}/{pointInTimeManifestation}/{subtype}.{fileExtension}".
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public final class ManifestationEli implements Eli {

  private String agent;
  private String year;
  private String naturalIdentifier;
  private LocalDate pointInTime;
  private Integer version;
  private String language;
  private LocalDate pointInTimeManifestation;
  private String subtype;
  private String format = "xml";

  public ManifestationEli(
    String agent,
    String year,
    String naturalIdentifier,
    LocalDate pointInTime,
    Integer version,
    String language,
    String subtype,
    String format
  ) {
    this(agent, year, naturalIdentifier, pointInTime, version, language, null, subtype, format);
  }

  /**
   * Does the eli contain a point-in-time-manifestation?
   *
   * @return true if it has a point-in-time-manifestation
   */
  public boolean hasPointInTimeManifestation() {
    return pointInTimeManifestation != null;
  }

  @Override
  public String toString() {
    if (!hasPointInTimeManifestation()) {
      return "eli/bund/%s/%s/%s/%s/%d/%s/%s.%s".formatted(
          getAgent(),
          getYear(),
          getNaturalIdentifier(),
          getPointInTime().format(DateTimeFormatter.ISO_LOCAL_DATE),
          getVersion(),
          getLanguage(),
          getSubtype(),
          getFormat()
        );
    }

    return "eli/bund/%s/%s/%s/%s/%d/%s/%s/%s.%s".formatted(
        getAgent(),
        getYear(),
        getNaturalIdentifier(),
        getPointInTime().format(DateTimeFormatter.ISO_LOCAL_DATE),
        getVersion(),
        getLanguage(),
        getPointInTimeManifestation().format(DateTimeFormatter.ISO_LOCAL_DATE),
        getSubtype(),
        getFormat()
      );
  }

  /**
   * Create the URI for the eli to be used in e.g. href attributes.
   *
   * @return the URI for the eli
   */
  @Override
  public URI toUri() {
    return URI.create(toString());
  }

  /**
   * Create a {@link ManifestationEli} that contains the parts of this eli but no point-in-time-manifestation
   *
   * @return a manifestation eli without a point-in-time-manifestation
   */
  public ManifestationEli withoutPointInTimeManifestation() {
    if (!hasPointInTimeManifestation()) {
      return this;
    }

    return new ManifestationEli(
      getAgent(),
      getYear(),
      getNaturalIdentifier(),
      getPointInTime(),
      getVersion(),
      getLanguage(),
      getSubtype(),
      getFormat()
    );
  }

  /**
   * Create an {@link ExpressionEli} that contains the parts of this eli
   *
   * @return an expression eli
   */
  public ExpressionEli asExpressionEli() {
    return new ExpressionEli(
      getAgent(),
      getYear(),
      getNaturalIdentifier(),
      getPointInTime(),
      getVersion(),
      getLanguage(),
      getSubtype()
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
   * Create a manifestation level eli from a string representation
   *
   * @param manifestationEli the string representation of the eli
   * @return the eli
   */
  public static ManifestationEli fromString(String manifestationEli) {
    Matcher matcher = Pattern
      .compile(
        "eli/bund/(?<agent>[^/]+)/(?<year>[^/]+)/(?<naturalIdentifier>[^/]+)/(?<pointInTime>[^/]+)/(?<version>[^/]+)/(?<language>[^/]+)(/(?<pointInTimeManifestation>[^/.]+))?/(?<subtype>[^/.]+)\\.(?<format>[^/.]+)"
      )
      .matcher(manifestationEli);

    if (!matcher.matches()) {
      throw new IllegalArgumentException("Invalid manifestation Eli");
    }

    return new ManifestationEli(
      matcher.group("agent"),
      matcher.group("year"),
      matcher.group("naturalIdentifier"),
      LocalDate.parse(matcher.group("pointInTime"), DateTimeFormatter.ISO_LOCAL_DATE),
      Integer.valueOf(matcher.group("version")),
      matcher.group("language"),
      matcher.group("pointInTimeManifestation") == null
        ? null
        : LocalDate.parse(
          matcher.group("pointInTimeManifestation"),
          DateTimeFormatter.ISO_LOCAL_DATE
        ),
      matcher.group("subtype"),
      matcher.group("format")
    );
  }

  /**
   * Create a manifestation level eli from an expression eli and the additional data for a manifestation eli.
   *
   * @param expressionEli            the expression eli to use as a base
   * @param pointInTimeManifestation the date the manifestation was created
   * @param format                   the file extension used by this manifestation
   * @return the eli
   */
  public static ManifestationEli fromExpressionEli(
    ExpressionEli expressionEli,
    LocalDate pointInTimeManifestation,
    String format
  ) {
    return new ManifestationEli(
      expressionEli.getAgent(),
      expressionEli.getYear(),
      expressionEli.getNaturalIdentifier(),
      expressionEli.getPointInTime(),
      expressionEli.getVersion(),
      expressionEli.getLanguage(),
      pointInTimeManifestation,
      expressionEli.getSubtype(),
      format
    );
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ManifestationEli that = (ManifestationEli) o;
    return Objects.equals(toString(), that.toString());
  }

  @Override
  public int hashCode() {
    return Objects.hash(toString());
  }
}
