package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.port.input.LoadNormExpressionsWorkingCopiesUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.*;
import de.bund.digitalservice.ris.norms.domain.entity.*;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import java.util.*;
import org.springframework.stereotype.Service;

/**
 * Service class with bundling functionality only relevant to a Zielnorm.
 */
@Service
public class ZielnormService implements LoadNormExpressionsWorkingCopiesUseCase {

  private final LoadNormPort loadNormPort;
  private final LoadNormExpressionElisPort loadNormExpressionElisPort;

  public ZielnormService(
    LoadNormPort loadNormPort,
    LoadNormExpressionElisPort loadNormExpressionElisPort
  ) {
    this.loadNormPort = loadNormPort;
    this.loadNormExpressionElisPort = loadNormExpressionElisPort;
  }

  /**
   * Loads all working copies of a given Zielnorm.
   * @param options containing the ELI of the Zielnorm
   * @return a list of working copies of the Zielnorm
   */
  @Override
  public List<Norm> loadZielnormWorkingCopies(
    final LoadNormExpressionsWorkingCopiesUseCase.Options options
  ) {
    List<NormExpressionEli> expressionElis = loadNormExpressionElisPort.loadNormExpressionElis(
      new LoadNormExpressionElisPort.Options(options.eli())
    );
    return expressionElis
      .stream()
      // we assume the latest expression is the working copy
      .map(expressionEli -> loadNormPort.loadNorm(new LoadNormPort.Options(expressionEli)))
      .flatMap(Optional::stream)
      .filter(norm -> NormPublishState.UNPUBLISHED.equals(norm.getPublishState()))
      .toList();
  }
}
