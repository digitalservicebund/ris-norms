package de.bund.digitalservice.ris.norms.domain.entity;

import java.time.LocalDate;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

/** Class representing a time boundary. */
@Getter
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class Zeitgrenze {

  private final Id id;
  private final LocalDate date;
  private final Art art;
  /**
   * Is the Zeitgrenze in use by a {@link ZielnormReference}?
   */
  private final boolean inUse;

  /**
   * The possible values for Art
   */
  public enum Art {
    INKRAFT,
    AUSSERKRAFT,
  }

  /**
   * Id of a {@link Zeitgrenze}
   * @param value the value of the id
   */
  public record Id(String value) {
    @Override
    public String toString() {
      return value;
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Zeitgrenze that = (Zeitgrenze) o;
    return art == that.art && Objects.equals(id, that.id) && Objects.equals(date, that.date);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, date, art);
  }
}
