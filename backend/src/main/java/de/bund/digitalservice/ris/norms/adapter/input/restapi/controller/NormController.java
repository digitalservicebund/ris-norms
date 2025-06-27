package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper.NormWorkResponseMapper;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.NormWorkResponseSchema;
import de.bund.digitalservice.ris.norms.application.port.input.LoadNormWorksUseCase;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for getting all norms
 */
@RestController
@RequestMapping("/api/v1/norms")
public class NormController {

  private final LoadNormWorksUseCase loadNormWorksUseCase;

  public NormController(LoadNormWorksUseCase loadNormWorksUseCase) {
    this.loadNormWorksUseCase = loadNormWorksUseCase;
  }

  /**
   * Get a list of all norms (work-level)
   * @param pageable pagination options to only load a subset of all norms at a time
   * @return a paged list of information about norms
   */
  @GetMapping(produces = { APPLICATION_JSON_VALUE })
  public PagedModel<NormWorkResponseSchema> getNorm(Pageable pageable) {
    return new PagedModel<>(
      loadNormWorksUseCase
        .loadNormWorks(
          new LoadNormWorksUseCase.Options(
            // ignore sort options
            Pageable.ofSize(pageable.getPageSize()).withPage(pageable.getPageNumber())
          )
        )
        .map(NormWorkResponseMapper::fromUseCaseData)
    );
  }
}
