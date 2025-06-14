package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper.ExpressionsStatusResponseMapper;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper.ReleaseResponseMapper;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.ReleaseResponseSchema;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.ZielnormReleaseStatusResponseSchema;
import de.bund.digitalservice.ris.norms.application.port.input.LoadNormExpressionsWorkingCopiesUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadNormUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadReleasesByNormExpressionEliUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.ReleaseNormExpressionUseCase;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.Verkuendung;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormWorkEli;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Controller for release-related actions. */
@RestController
@RequestMapping("/api/v1/eli/bund/{agent}/{year}/{naturalIdentifier}")
public class ReleaseController {

  private final LoadReleasesByNormExpressionEliUseCase loadReleasesByNormExpressionEliUseCase;
  private final ReleaseNormExpressionUseCase releaseNormExpressionUseCase;
  private final LoadNormExpressionsWorkingCopiesUseCase loadNormExpressionsWorkingCopiesUseCase;
  private final LoadNormUseCase loadNormUseCase;

  public ReleaseController(
    LoadReleasesByNormExpressionEliUseCase loadReleasesByNormExpressionEliUseCase,
    ReleaseNormExpressionUseCase releaseNormExpressionUseCase,
    LoadNormExpressionsWorkingCopiesUseCase loadNormExpressionsWorkingCopiesUseCase,
    LoadNormUseCase loadNormUseCase
  ) {
    this.loadReleasesByNormExpressionEliUseCase = loadReleasesByNormExpressionEliUseCase;
    this.releaseNormExpressionUseCase = releaseNormExpressionUseCase;
    this.loadNormExpressionsWorkingCopiesUseCase = loadNormExpressionsWorkingCopiesUseCase;
    this.loadNormUseCase = loadNormUseCase;
  }

  /**
   * Retrieves all releases of a {@link Norm} expression.
   *
   * @param eli Eli of the request
   * @return A {@link ResponseEntity} containing the retrieved release.
   *     <p>Returns HTTP 200 (OK) and the release if found.
   *     <p>Returns HTTP 404 (Not Found) if no release is found.
   */
  @SuppressWarnings("java:S6856")
  @GetMapping(
    path = "/{pointInTime}/{version}/{language}/releases",
    produces = { APPLICATION_JSON_VALUE }
  )
  public ResponseEntity<List<ReleaseResponseSchema>> getReleases(final NormExpressionEli eli) {
    var releases = loadReleasesByNormExpressionEliUseCase.loadReleasesByNormExpressionEli(
      new LoadReleasesByNormExpressionEliUseCase.Options(eli)
    );

    return ResponseEntity.ok(releases.stream().map(ReleaseResponseMapper::fromRelease).toList());
  }

  /**
   * Releases a new release of the norm.
   *
   * @param eli Eli of the request
   * @return A {@link ResponseEntity} containing the created release.
   *     <p>Returns HTTP 200 (OK) and the release was successful.
   *     <p>Returns HTTP 404 (Not Found) if no {@link Verkuendung} is found.
   */
  @SuppressWarnings("java:S6856")
  @PostMapping(
    path = "/{pointInTime}/{version}/{language}/releases",
    produces = { APPLICATION_JSON_VALUE }
  )
  public ResponseEntity<ReleaseResponseSchema> postReleases(final NormExpressionEli eli) {
    var release = releaseNormExpressionUseCase.releaseNormExpression(
      new ReleaseNormExpressionUseCase.Options(eli)
    );
    return ResponseEntity.ok(ReleaseResponseMapper.fromRelease(release));
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
