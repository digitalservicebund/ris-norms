package de.bund.digitalservice.ris.norms.domain.entity.eli;

import java.util.Objects;
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
