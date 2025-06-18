package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.port.output.LoadNormExpressionElisPort;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormWorkEli;
import java.time.LocalDate;
import java.util.Comparator;
import org.springframework.stereotype.Service;

/**
 * Service for finding the next available eli.
 */
@Service
public class EliService {

  private final LoadNormExpressionElisPort loadNormExpressionElisPort;

  public EliService(LoadNormExpressionElisPort loadNormExpressionElisPort) {
    this.loadNormExpressionElisPort = loadNormExpressionElisPort;
  }

  /**
   * Finds the next available {@link NormExpressionEli} for the given date and language.
   * Uses the lowest available version number that is not in use.
   *
   * @param workEli     the work eli that should be used as the base for the expression eli
   * @param pointInTime the date of the verkündung.
   * @param language    the language of the expression.
   * @return a new {@link NormExpressionEli} for the given date.
   */
  public NormExpressionEli findNextExpressionEli(
    NormWorkEli workEli,
    LocalDate pointInTime,
    String language
  ) {
    var expressionsOfWork = loadNormExpressionElisPort.loadNormExpressionElis(
      new LoadNormExpressionElisPort.Options(workEli)
    );

    var largestOccupiedVersion = expressionsOfWork
      .stream()
      .filter(expressionEli -> expressionEli.getPointInTime().isEqual(pointInTime))
      .filter(expressionEli -> expressionEli.getLanguage().equals(language))
      .map(NormExpressionEli::getVersion)
      .max(Comparator.naturalOrder())
      .orElse(0);

    return NormExpressionEli.fromWorkEli(
      workEli,
      pointInTime,
      largestOccupiedVersion + 1,
      language
    );
  }
}
