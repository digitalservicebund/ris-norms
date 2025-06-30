package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper.NormWorkResponseMapper;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.NormWorkResponseSchema;
import de.bund.digitalservice.ris.norms.application.port.input.LoadNormUseCase;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormWorkEli;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for getting all norms
 */
@RestController
@RequestMapping("/api/v1/norms/eli/bund/{agent}/{year}/{naturalIdentifier}")
public class NormWorkController {

  private final LoadNormUseCase loadNormUseCase;

  public NormWorkController(LoadNormUseCase loadNormUseCase) {
    this.loadNormUseCase = loadNormUseCase;
  }

  /**
   * Get information about a norm (work-level)
   * @param eli the eli of the work to get information about
   * @return information about a norm
   */
  @GetMapping(produces = { APPLICATION_JSON_VALUE })
  public ResponseEntity<NormWorkResponseSchema> getNorm(NormWorkEli eli) {
    return ResponseEntity.ok(
      NormWorkResponseMapper.fromUseCaseData(
        loadNormUseCase.loadNorm(new LoadNormUseCase.EliOptions(eli))
      )
    );
  }
}
