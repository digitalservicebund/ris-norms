package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.ZielnormReference;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormWorkEli;
import java.util.List;
import javax.annotation.Nullable;

/**
 *  Use case for getting information about the Zielnormen that will be created or set to gegenstandslos when applying the {@link ZielnormReference}s of the given Verkündung.
 */
public interface LoadZielnormenPreviewUseCase {
  /**
   * Retrieves information about the Zielnormen that will be created or set to gegenstandslos.
   *
   * @param query Query used for identifying the norm
   * @return The preview information
   */
  List<ZielnormPreview> loadZielnormenPreview(Query query);

  /**
   * Contains the parameters needed for identifying the Verkündung.
   *
   * @param eli The ELI used to identify the Verkündung
   */
  record Query(NormExpressionEli eli) {}

  /**
   * Information about the Zielnorm that will be created or set to gegenstandslos when applying the {@link ZielnormReference}s of the given Verkündung
   * @param normWorkEli the eli of the Zielnorm
   * @param title the title of the Zielnorm
   * @param shortTitle the short title of the Zielnorm
   * @param expressions the expressions of the Zielnorm that will be either created of set to gegenstandslos
   */
  record ZielnormPreview(
    NormWorkEli normWorkEli,
    @Nullable String title,
    @Nullable String shortTitle,
    List<Expression> expressions
  ) {
    /**
     * Information on one expression of the Zielnorm
     * @param normExpressionEli the eli of the expression
     * @param isGegenstandslos will the expression be gegenstandslos once the zielnorm-references are applied
     * @param isCreated does this expression already exist in the system
     * @param createdBy the thing that created or creates this expression
     */
    public record Expression(
      NormExpressionEli normExpressionEli,
      boolean isGegenstandslos,
      boolean isCreated,
      CreatedBy createdBy
    ) {}

    /**
     * Explanation for the reason that this expression will be set to gegenstandslos or be created
     */
    public enum CreatedBy {
      /**
       * It's created automatically by the system. Usually when replacing a now gegenstandslose expression
       */
      SYSTEM,
      /**
       * It's created due to a {@link ZielnormReference} that uses this date
       */
      THIS_VERKUENDUNG,
      /**
       * An existing expression, created by a different Verkündung, that will be set to gegenstandslos
       */
      OTHER_VERKUENDUNG,
    }
  }
}
