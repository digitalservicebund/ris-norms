package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper.ExpressionsStatusResponseMapper;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.ReleaseRequestSchema;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.ZielnormReleaseStatusResponseSchema;
import de.bund.digitalservice.ris.norms.application.port.input.LoadNormExpressionsWorkingCopiesUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadNormUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.ReleaseAllNormExpressionsUseCase;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.Verkuendung;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormWorkEli;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** Controller for release-related actions. */
@RestController
@RequestMapping("/api/v1/eli/bund/{agent}/{year}/{naturalIdentifier}")
public class ReleaseController {

  private final ReleaseAllNormExpressionsUseCase releaseAllNormExpressionsUseCase;
  private final LoadNormExpressionsWorkingCopiesUseCase loadNormExpressionsWorkingCopiesUseCase;
  private final LoadNormUseCase loadNormUseCase;

  public ReleaseController(
    ReleaseAllNormExpressionsUseCase releaseAllNormExpressionsUseCase,
    LoadNormExpressionsWorkingCopiesUseCase loadNormExpressionsWorkingCopiesUseCase,
    LoadNormUseCase loadNormUseCase
  ) {
    this.releaseAllNormExpressionsUseCase = releaseAllNormExpressionsUseCase;
    this.loadNormExpressionsWorkingCopiesUseCase = loadNormExpressionsWorkingCopiesUseCase;
    this.loadNormUseCase = loadNormUseCase;
  }

  /**
   * Releases a new release of the norm.
   *
   * @param eli Eli of the request
   * @param requestBody the request body containing the release type
   * @return A {@link ResponseEntity} containing the created release.
   *     <p>Returns HTTP 200 (OK) and the release was successful.
   *     <p>Returns HTTP 404 (Not Found) if no {@link Verkuendung} is found.
   */
  @PostMapping(path = "/releases", produces = { APPLICATION_JSON_VALUE })
  public ResponseEntity<ZielnormReleaseStatusResponseSchema> postReleasesForNorm(
    final NormWorkEli eli,
    @RequestBody ReleaseRequestSchema requestBody
  ) {
    var publishedNorms = releaseAllNormExpressionsUseCase.release(
      new ReleaseAllNormExpressionsUseCase.Options(eli, requestBody.releaseType())
    );
    if (publishedNorms.isEmpty()) {
      Norm norm = loadNormUseCase.loadNorm(new LoadNormUseCase.EliOptions(eli));
      return ResponseEntity.ok(ExpressionsStatusResponseMapper.fromPublishedNorm(norm));
    } else {
      return ResponseEntity.ok(ExpressionsStatusResponseMapper.fromNorms(publishedNorms));
    }
  }

  /**
   * Retrieves the list of all UNPUBLISHED expressions to a work eli.
   *
   * @param zielnormEli the work eli of the Zielnorm
   * @return A {@link ResponseEntity} containing the response schema for a list of {@link ZielnormReleaseStatusResponseSchema}s.
   *     <p>Returns HTTP 200 (OK) and list of {@link ZielnormReleaseStatusResponseSchema}s.
   *     <p>Returns HTTP 404 (Not Found) if the verkuendung is not found.
   */
  @GetMapping(value = "/expressions/releasestatus", produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<ZielnormReleaseStatusResponseSchema> getZielnormExpressionsStatus(
    NormWorkEli zielnormEli
  ) {
    var workingCopies = loadNormExpressionsWorkingCopiesUseCase.loadZielnormWorkingCopies(
      new LoadNormExpressionsWorkingCopiesUseCase.Options(zielnormEli)
    );
    if (workingCopies.isEmpty()) {
      Norm norm = loadNormUseCase.loadNorm(new LoadNormUseCase.EliOptions(zielnormEli));
      return ResponseEntity.ok(ExpressionsStatusResponseMapper.fromPublishedNorm(norm));
    } else {
      return ResponseEntity.ok(ExpressionsStatusResponseMapper.fromNorms(workingCopies));
    }
  }
}
