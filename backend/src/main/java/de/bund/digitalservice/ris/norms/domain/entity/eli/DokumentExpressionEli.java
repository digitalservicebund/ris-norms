package de.bund.digitalservice.ris.norms.domain.entity.eli;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
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
