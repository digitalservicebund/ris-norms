package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.exception.NormNotFoundException;
import de.bund.digitalservice.ris.norms.application.port.input.LoadNormUseCase;
import de.bund.digitalservice.ris.norms.domain.entity.eli.ExpressionEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.WorkEli;
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
   * Finds the next available {@link ExpressionEli} for the given date and language.
   * Uses the lowest available the version number that is not in use.
   *
   * @param workEli     the work eli that should be used as the base for the expression eli
   * @param pointInTime the date of the verkündung.
   * @param language    the language of the expression.
   * @return a new {@link ExpressionEli} for the given date.
   */
  public ExpressionEli findNextExpressionEli(
    WorkEli workEli,
    LocalDate pointInTime,
    String language
  ) {
    var expressionEli = ExpressionEli.fromWorkEli(workEli, pointInTime, 1, language);

    while (true) {
      try {
        loadNormUseCase.loadNorm(new LoadNormUseCase.Query(expressionEli));
        expressionEli.setVersion(expressionEli.getVersion() + 1);
      } catch (NormNotFoundException e) {
        return expressionEli;
      }
    }
  }
}
