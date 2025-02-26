package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper.ReleaseResponseMapper;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.ReleaseResponseSchema;
import de.bund.digitalservice.ris.norms.application.port.input.LoadReleasesByNormExpressionEliUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.ReleaseNormExpressionUseCase;
import de.bund.digitalservice.ris.norms.domain.entity.Announcement;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Controller for release-related actions. */
@RestController
@RequestMapping(
  "/api/v1/eli/bund/{agent}/{year}/{naturalIdentifier}/{pointInTime}/{version}/{language}/releases"
)
public class ReleaseController {

  private final LoadReleasesByNormExpressionEliUseCase loadReleasesByNormExpressionEliUseCase;
  private final ReleaseNormExpressionUseCase releaseNormExpressionUseCase;

  public ReleaseController(
    LoadReleasesByNormExpressionEliUseCase loadReleasesByNormExpressionEliUseCase,
    ReleaseNormExpressionUseCase releaseNormExpressionUseCase
  ) {
    this.loadReleasesByNormExpressionEliUseCase = loadReleasesByNormExpressionEliUseCase;
    this.releaseNormExpressionUseCase = releaseNormExpressionUseCase;
  }

  /**
   * Retrieves all releases of a {@link Norm} expression.
   *
   * @param eli Eli of the request
   * @return A {@link ResponseEntity} containing the retrieved release.
   *     <p>Returns HTTP 200 (OK) and the release if found.
   *     <p>Returns HTTP 404 (Not Found) if no release is found.
   */
  @GetMapping(produces = { APPLICATION_JSON_VALUE })
  public ResponseEntity<List<ReleaseResponseSchema>> getReleases(final NormExpressionEli eli) {
    var releases = loadReleasesByNormExpressionEliUseCase.loadReleasesByNormExpressionEli(
      new LoadReleasesByNormExpressionEliUseCase.Query(eli)
    );

    return ResponseEntity.ok(releases.stream().map(ReleaseResponseMapper::fromRelease).toList());
  }

  /**
   * Releases a new release of the norm.
   *
   * @param eli Eli of the request
   * @return A {@link ResponseEntity} containing the created release.
   *     <p>Returns HTTP 200 (OK) and the release was successful.
   *     <p>Returns HTTP 404 (Not Found) if no {@link Announcement} is found.
   */
  @PostMapping(produces = { APPLICATION_JSON_VALUE })
  public ResponseEntity<ReleaseResponseSchema> postReleases(final NormExpressionEli eli) {
    var release = releaseNormExpressionUseCase.releaseNormExpression(
      new ReleaseNormExpressionUseCase.Query(eli)
    );
    return ResponseEntity.ok(ReleaseResponseMapper.fromRelease(release));
  }
}
