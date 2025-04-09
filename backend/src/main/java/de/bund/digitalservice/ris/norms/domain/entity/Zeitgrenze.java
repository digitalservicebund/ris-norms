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

  private final String id;
  private final LocalDate date;
  private final Art art;

  /**
   * The possible values for Art
   */
  public enum Art {
    INKRAFT,
    AUSSERKRAFT,
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
