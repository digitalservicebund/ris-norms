package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.ZielnormReference;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormWorkEli;
import java.util.List;
import javax.annotation.Nullable;

/**
 * Use case for creating the expressions of a {@link List} of Zielnormen (targeted legal norms) in response to changes introduced by a specified Verkündung (enactment).
 * <p>
 * A Verkündung contains new temporal boundaries (Zeitgrenzen) and references to Zielnormen it affects.
 * This use case updates these Zielnormen by creating new expressions or marking existing expressions as {@code gegenstandslos}
 * (obsolete/irrelevant) according to the new legal situation.
 * <p>
 * If the {@code dryRun} flag is set to {@code true}, the updates (creations and deactivations of expressions)
 * are not persisted in the database and are only returned as a preview. Otherwise, the changes are saved permanently.
 */
public interface CreateZielnormenExpressionsUseCase {
  /**
   * Creates or sets to gegenstandslos the expressions of a Zielnorm according to the Verkündung that changes them.
   *
   * @param query Query used for identifying the Verkündung and if the created expressions should actually be saved to the database.
   * @return a shortened form of the affected Zielnorms with the expressions that were created or set to gegenstandslos.
   */
  List<Zielnorm> createZielnormExpressions(Query query);

  /**
   * Contains the parameters needed for identifying the Verkündung and if the created expressions should actually be saved to the database.
   *
   * @param eli The ELI used to identify the Verkündung
   * @param dryRun If true, the preview will be generated without making any changes to the database
   */
  record Query(NormExpressionEli eli, boolean dryRun) {
    /**
     * Constructor with default value for dryRun.
     *
     * @param eli The ELI used to identify the Verkündung
     */
    public Query(NormExpressionEli eli) {
      this(eli, true);
    }
  }

  /**
   * Selected Zielnorm information
   * @param normWorkEli the eli of the Zielnorm
   * @param title the title of the Zielnorm
   * @param shortTitle the short title of the Zielnorm
   * @param expressions the expressions of the Zielnorm that will be either created of set to gegenstandslos
   */
  record Zielnorm(
    NormWorkEli normWorkEli,
    @Nullable String title,
    @Nullable String shortTitle,
    List<Expression> expressions
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
      CreatedBy createdBy
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
}
