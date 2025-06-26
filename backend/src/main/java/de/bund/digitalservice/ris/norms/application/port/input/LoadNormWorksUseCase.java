package de.bund.digitalservice.ris.norms.application.port.input;

import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Use case for loading a paginated list of all norms (on work level)
 */
public interface LoadNormWorksUseCase {
  /**
   * Loads {@link Norm}s.
   *
   * @param options The options specifying the page of norms to be loaded.
   * @return A {@link Page} containing the loaded {@link Norm}s.
   */
  Page<Norm> loadNormWorks(Options options);

  /**
   * Options for loading norms.
   *
   * @param pageable the pagination information
   */
  record Options(Pageable pageable) {}
}
