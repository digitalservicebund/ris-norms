package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.springframework.http.MediaType.*;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper.NormResponseMapper;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.NormResponseSchema;
import de.bund.digitalservice.ris.norms.application.port.input.LoadNormUseCase;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for norm-related actions identifying the norm by its GUID.
 */
@RestController
@RequestMapping("/api/v1/norms/{guid}")
public class NormGuidController {

  private final LoadNormUseCase loadNormUseCase;

  public NormGuidController(LoadNormUseCase loadNormUseCase) {
    this.loadNormUseCase = loadNormUseCase;
  }

  /**
   * Retrieves a norm based on its GUID.
   *
   * @param guid GUID of the request
   * @return A {@link ResponseEntity} containing the retrieved norm.
   *     <p>Returns HTTP 200 (OK) and the norm if found.
   *     <p>Returns HTTP 404 (Not Found) if the norm is not found.
   */
  @GetMapping(produces = { APPLICATION_JSON_VALUE })
  public ResponseEntity<NormResponseSchema> getNorm(@PathVariable final UUID guid) {
    var norm = loadNormUseCase.loadNorm(new LoadNormUseCase.GuidQuery(guid));
    return ResponseEntity.ok(NormResponseMapper.fromUseCaseData(norm));
  }
}
