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
 * European legislation identifier on work level for a norm
 *
 * <p>This class can be used to extract the eli from a path that includes a section like
 * "/eli/bund/{agent}/{year}/{naturalIdentifier}".
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public final class NormWorkEli implements NormEli, Comparable<NormWorkEli> {

  private String agent;
  private String year;
  private String naturalIdentifier;

  /**
   * Create a work level eli from a string representation
   *
   * @param workEli the string representation of the eli
   * @return the eli
   */
  public static NormWorkEli fromString(String workEli) {
    Matcher matcher = Pattern.compile(
      "eli/bund/(?<agent>[^/]+)/(?<year>[^/]+)/(?<naturalIdentifier>[^/]+)"
    ).matcher(workEli);

    if (!matcher.matches()) {
      throw new InvalidEliException(NormWorkEli.class, workEli);
    }

    return new NormWorkEli(
      matcher.group("agent"),
      matcher.group("year"),
      matcher.group("naturalIdentifier")
    );
  }

  @Override
  public String toString() {
    return "eli/bund/%s/%s/%s".formatted(getAgent(), getYear(), getNaturalIdentifier());
  }

  @Override
  public URI toUri() {
    return URI.create(toString());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    NormWorkEli that = (NormWorkEli) o;
    return Objects.equals(toString(), that.toString());
  }

  @Override
  public int hashCode() {
    return Objects.hash(toString());
  }

  @Override
  public int compareTo(@NotNull NormWorkEli o) {
    return String.CASE_INSENSITIVE_ORDER.compare(this.toString(), o.toString());
  }
}
