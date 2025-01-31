package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.exception.NormNotFoundException;
import de.bund.digitalservice.ris.norms.application.port.input.LoadNormUseCase;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormWorkEli;
import java.time.LocalDate;
import org.springframework.stereotype.Service;

/**
 * Service for finding the next available eli.
 */
@Service
public class EliService {

  private final LoadNormUseCase loadNormUseCase;

  public EliService(LoadNormUseCase loadNormUseCase) {
    this.loadNormUseCase = loadNormUseCase;
  }

  /**
   * Finds the next available {@link NormExpressionEli} for the given date and language.
   * Uses the lowest available the version number that is not in use.
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
    var expressionEli = NormExpressionEli.fromWorkEli(workEli, pointInTime, 1, language);

    for (int i = 0; i < 1000; i++) {
      try {
        loadNormUseCase.loadNorm(new LoadNormUseCase.Query(expressionEli));
        expressionEli.setVersion(expressionEli.getVersion() + 1);
      } catch (NormNotFoundException e) {
        return expressionEli;
      }
    }

    throw new RuntimeException(
      "Could not determine next available expression eli version. Checked up to %s".formatted(
          expressionEli
        )
    );
  }
}
