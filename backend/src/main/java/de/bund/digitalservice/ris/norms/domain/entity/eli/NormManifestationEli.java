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
 * European legislation identifier on manifestation level for a norm
 *
 * <p>This class can be used to extract the eli from a path that includes a section like
 * "/eli/bund/{agent}/{year}/{naturalIdentifier}/{pointInTime}/{version}/{language}/{pointInTimeManifestation}".
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public final class NormManifestationEli implements NormEli, Comparable<NormManifestationEli> {

  private String agent;
  private String year;
  private String naturalIdentifier;
  private LocalDate pointInTime;
  private Integer version;
  private String language;
  private LocalDate pointInTimeManifestation;

  public NormManifestationEli(
    String agent,
    String year,
    String naturalIdentifier,
    LocalDate pointInTime,
    Integer version,
    String language
  ) {
    this(agent, year, naturalIdentifier, pointInTime, version, language, null);
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
      return "eli/bund/%s/%s/%s/%s/%d/%s".formatted(
          getAgent(),
          getYear(),
          getNaturalIdentifier(),
          getPointInTime().format(DateTimeFormatter.ISO_LOCAL_DATE),
          getVersion(),
          getLanguage()
        );
    }

    return "eli/bund/%s/%s/%s/%s/%d/%s/%s".formatted(
        getAgent(),
        getYear(),
        getNaturalIdentifier(),
        getPointInTime().format(DateTimeFormatter.ISO_LOCAL_DATE),
        getVersion(),
        getLanguage(),
        getPointInTimeManifestation().format(DateTimeFormatter.ISO_LOCAL_DATE)
      );
  }

  @Override
  public URI toUri() {
    return URI.create(toString());
  }

  /**
   * Create a {@link NormManifestationEli} that contains the parts of this eli but no point-in-time-manifestation
   *
   * @return a manifestation eli without a point-in-time-manifestation
   */
  public NormManifestationEli withoutPointInTimeManifestation() {
    if (!hasPointInTimeManifestation()) {
      return this;
    }

    return new NormManifestationEli(
      getAgent(),
      getYear(),
      getNaturalIdentifier(),
      getPointInTime(),
      getVersion(),
      getLanguage()
    );
  }

  /**
   * Create an {@link NormExpressionEli} that contains the parts of this eli
   *
   * @return an expression eli
   */
  public NormExpressionEli asExpressionEli() {
    return new NormExpressionEli(
      getAgent(),
      getYear(),
      getNaturalIdentifier(),
      getPointInTime(),
      getVersion(),
      getLanguage()
    );
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
   * Create a manifestation level eli from a string representation
   *
   * @param manifestationEli the string representation of the eli
   * @return the eli
   */
  public static NormManifestationEli fromString(String manifestationEli) {
    Matcher matcher = Pattern
      .compile(
        "eli/bund/(?<agent>[^/]+)/(?<year>[^/]+)/(?<naturalIdentifier>[^/]+)/(?<pointInTime>[^/]+)/(?<version>[^/]+)/(?<language>[^/]+)(/(?<pointInTimeManifestation>[^/.]+))?"
      )
      .matcher(manifestationEli);

    if (!matcher.matches()) {
      throw new IllegalArgumentException("Invalid manifestation Eli");
    }

    return new NormManifestationEli(
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
        )
    );
  }

  /**
   * Create a manifestation level eli from an expression eli and the additional data for a manifestation eli.
   *
   * @param expressionEli            the expression eli to use as a base
   * @param pointInTimeManifestation the date the manifestation was created
   * @return the eli
   */
  public static NormManifestationEli fromExpressionEli(
    NormExpressionEli expressionEli,
    LocalDate pointInTimeManifestation
  ) {
    return new NormManifestationEli(
      expressionEli.getAgent(),
      expressionEli.getYear(),
      expressionEli.getNaturalIdentifier(),
      expressionEli.getPointInTime(),
      expressionEli.getVersion(),
      expressionEli.getLanguage(),
      pointInTimeManifestation
    );
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    NormManifestationEli that = (NormManifestationEli) o;
    return Objects.equals(toString(), that.toString());
  }

  @Override
  public int hashCode() {
    return Objects.hash(toString());
  }

  @Override
  public int compareTo(@NotNull NormManifestationEli o) {
    return String.CASE_INSENSITIVE_ORDER.compare(this.toString(), o.toString());
  }
}
