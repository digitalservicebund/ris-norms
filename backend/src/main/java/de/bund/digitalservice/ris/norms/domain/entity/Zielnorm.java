package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormWorkEli;
import java.util.List;
import javax.annotation.Nullable;

/**
 * Selected Zielnorm information
 * @param normWorkEli the eli of the Zielnorm
 * @param title the title of the Zielnorm
 * @param shortTitle the short title of the Zielnorm
 * @param expressions the expressions of the Zielnorm that will be either created of set to gegenstandslos
 */
public record Zielnorm(
  NormWorkEli normWorkEli,
  @Nullable String title,
  @Nullable String shortTitle,
  List<Zielnorm.Expression> expressions
) {
  /**
   * Selected information on the expression that was created or set to gegenstandslos.
   * @param normExpressionEli the eli of the expression
   * @param isGegenstandslos will the expression be gegenstandslos
   * @param isCreated did this expression already exist in the database before this run
   * @param createdBy the thing that created or creates this expression
   */
  public record Expression(
    NormExpressionEli normExpressionEli,
    boolean isGegenstandslos,
    boolean isCreated,
    Zielnorm.CreatedBy createdBy
  ) {}

  /**
   * The reason that this expression will be set to gegenstandslos or be created
   */
  public enum CreatedBy {
    /**
     * It's created automatically by the system. Usually when replacing a now gegenstandslose expression
     */
    SYSTEM,
    /**
     * It's created due to a {@link ZielnormReference} (Zeitgrenze) that uses this date
     */
    THIS_VERKUENDUNG,
    /**
     * An existing expression, created by a different Verkündung, that will be set to gegenstandslos
     */
    OTHER_VERKUENDUNG,
  }
}
