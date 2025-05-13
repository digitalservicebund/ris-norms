package de.bund.digitalservice.ris.norms.domain.entity.eli;

import de.bund.digitalservice.ris.norms.utils.exceptions.InvalidEliException;
import java.net.URI;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

/**
 * European legislation identifier on work level for a dokument of a norm
 *
 * <p>This class can be used to extract the eli from a path that includes a section like
 * "/eli/bund/{agent}/{year}/{naturalIdentifier}/{subtype}".
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public final class DokumentWorkEli implements DokumentEli, Comparable<DokumentWorkEli> {

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
  public static DokumentWorkEli fromString(String workEli) {
    Matcher matcher = Pattern
      .compile(
        "eli/bund/(?<agent>[^/]+)/(?<year>[^/]+)/(?<naturalIdentifier>[^/]+)/(?<subtype>[^/]+)"
      )
      .matcher(workEli);

    if (!matcher.matches()) {
      throw new InvalidEliException(DokumentWorkEli.class, workEli);
    }

    return new DokumentWorkEli(
      matcher.group("agent"),
      matcher.group("year"),
      matcher.group("naturalIdentifier"),
      matcher.group("subtype")
    );
  }

  /**
   * Create an eli for a Dokument from the eli for a norm.
   * @param normEli the eli for the norm of the Dokument
   * @param subtype the subtype of the Dokument
   * @return the eli for the document
   */
  public static DokumentWorkEli fromNormEli(NormWorkEli normEli, String subtype) {
    return new DokumentWorkEli(
      normEli.getAgent(),
      normEli.getYear(),
      normEli.getNaturalIdentifier(),
      subtype
    );
  }

  /**
   * Create a {@link NormWorkEli} that contains the parts of this eli
   *
   * @return a norm eli
   */
  @Override
  public NormWorkEli asNormEli() {
    return new NormWorkEli(getAgent(), getYear(), getNaturalIdentifier());
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
  @Override
  public URI toUri() {
    return URI.create("eli/bund/%s/%s/%s".formatted(getAgent(), getYear(), getNaturalIdentifier()));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    DokumentWorkEli that = (DokumentWorkEli) o;
    return Objects.equals(toString(), that.toString());
  }

  @Override
  public int hashCode() {
    return Objects.hash(toString());
  }

  @Override
  public int compareTo(@NotNull DokumentWorkEli o) {
    return String.CASE_INSENSITIVE_ORDER.compare(this.toString(), o.toString());
  }
}
