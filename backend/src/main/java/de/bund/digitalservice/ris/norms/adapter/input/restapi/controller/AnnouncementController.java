package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.springframework.http.MediaType.*;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper.NormResponseMapper;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper.ReleaseResponseMapper;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.NormResponseSchema;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.ReleaseResponseSchema;
import de.bund.digitalservice.ris.norms.application.port.input.CreateAnnouncementUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadAllAnnouncementsUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadAnnouncementByNormEliUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadNormUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.ReleaseAnnouncementUseCase;
import de.bund.digitalservice.ris.norms.domain.entity.Announcement;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.Release;
import de.bund.digitalservice.ris.norms.domain.entity.eli.ExpressionEli;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/** Controller for announcement-related actions. */
@RestController
@RequestMapping("/api/v1/announcements")
public class AnnouncementController {

  private final LoadAllAnnouncementsUseCase loadAllAnnouncementsUseCase;
  private final LoadAnnouncementByNormEliUseCase loadAnnouncementByNormEliUseCase;
  private final ReleaseAnnouncementUseCase releaseAnnouncementUseCase;
  private final CreateAnnouncementUseCase createAnnouncementUseCase;
  private final LoadNormUseCase loadNormUseCase;

  public AnnouncementController(
    LoadAllAnnouncementsUseCase loadAllAnnouncementsUseCase,
    LoadAnnouncementByNormEliUseCase loadAnnouncementByNormEliUseCase,
    ReleaseAnnouncementUseCase releaseAnnouncementUseCase,
    CreateAnnouncementUseCase createAnnouncementUseCase,
    LoadNormUseCase loadNormUseCase
  ) {
    this.loadAllAnnouncementsUseCase = loadAllAnnouncementsUseCase;
    this.loadAnnouncementByNormEliUseCase = loadAnnouncementByNormEliUseCase;
    this.releaseAnnouncementUseCase = releaseAnnouncementUseCase;
    this.createAnnouncementUseCase = createAnnouncementUseCase;
    this.loadNormUseCase = loadNormUseCase;
  }

  /**
   * Retrieves all available {@link Announcement}s
   *
   * @return A {@link ResponseEntity} containing the response schema for a list of {@link
   *     Announcement}s
   *     <p>Returns HTTP 200 (OK) and a list of {@link Announcement}s on successful execution.
   *     <p>If no announcement is found, the list is empty.
   */
  @GetMapping(produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<List<NormResponseSchema>> getAllAnnouncements() {
    var responseSchemas = loadAllAnnouncementsUseCase
      .loadAllAnnouncements()
      .stream()
      .map(Announcement::getEli)
      .map(LoadNormUseCase.Query::new)
      .map(loadNormUseCase::loadNorm)
      .map(NormResponseMapper::fromUseCaseData)
      .toList();
    return ResponseEntity.ok(responseSchemas);
  }

  /**
   * Retrieves the latest release of an {@link Announcement} based on its {@link Norm}'s expression ELI. The
   * ELI's components are interpreted as query parameters.
   *
   * <p>(German terms are taken from the LDML_de 1.6 specs, p146/147, cf. <a
   * href="https://github.com/digitalservicebund/ris-norms/commit/17778285381a674f1a2b742ed573b7d3d542ea24">...</a>)
   *
   * @param eli Eli of the request
   * @return A {@link ResponseEntity} containing the retrieved release.
   *     <p>Returns HTTP 200 (OK) and the release if found.
   *     <p>Returns HTTP 404 (Not Found) if no release is found.
   */
  @GetMapping(
    path = "/eli/bund/{agent}/{year}/{naturalIdentifier}/{pointInTime}/{version}/{language}/{subtype}/release",
    produces = { APPLICATION_JSON_VALUE }
  )
  public ResponseEntity<ReleaseResponseSchema> getRelease(final ExpressionEli eli) {
    var announcement = loadAnnouncementByNormEliUseCase.loadAnnouncementByNormEli(
      new LoadAnnouncementByNormEliUseCase.Query(eli)
    );
    var latestRelease = announcement
      .getReleases()
      .stream()
      .max(Comparator.comparing(Release::getReleasedAt));

    return latestRelease
      .map(ReleaseResponseMapper::fromRelease)
      .map(ResponseEntity::ok)
      .orElse(ResponseEntity.notFound().build());
  }

  /**
   * Releases an {@link Announcement} (and all related {@link Norm}'s) based on its {@link Norm}'s
   * expression ELI. The ELI's components are interpreted as query parameters.
   *
   * <p>(German terms are taken from the LDML_de 1.6 specs, p146/147, cf. <a
   * href="https://github.com/digitalservicebund/ris-norms/commit/17778285381a674f1a2b742ed573b7d3d542ea24">...</a>)
   *
   * @param eli Eli of the request
   * @return A {@link ResponseEntity} containing the created release.
   *     <p>Returns HTTP 200 (OK) and the release was successful.
   *     <p>Returns HTTP 404 (Not Found) if no {@link Announcement} is found.
   */
  @PutMapping(
    path = "/eli/bund/{agent}/{year}/{naturalIdentifier}/{pointInTime}/{version}/{language}/{subtype}/release",
    produces = { APPLICATION_JSON_VALUE }
  )
  public ResponseEntity<ReleaseResponseSchema> putRelease(final ExpressionEli eli) {
    var announcement = releaseAnnouncementUseCase.releaseAnnouncement(
      new ReleaseAnnouncementUseCase.Query(eli)
    );
    var latestRelease = announcement
      .getReleases()
      .stream()
      .max(Comparator.comparing(Release::getReleasedAt))
      .orElseThrow();

    return ResponseEntity.ok(ReleaseResponseMapper.fromRelease(latestRelease));
  }

  /**
   * Creates a new {@link Announcement} using the norm-file provided.
   *
   * @param file a file containing an amending norm as an XML file that contains LDML.de
   * @param force in case a norm already exists, if set to true, the amending norm will be overriden and the corresponding ZF0 deleted
   * @return information about the newly created announcement
   */
  @PostMapping(produces = { APPLICATION_JSON_VALUE })
  public ResponseEntity<NormResponseSchema> postAnnouncement(
    @RequestParam final MultipartFile file,
    @RequestParam(defaultValue = "false") final Boolean force
  ) throws IOException {
    var announcement = createAnnouncementUseCase.createAnnouncement(
      new CreateAnnouncementUseCase.Query(file, force)
    );
    var norm = loadNormUseCase.loadNorm(new LoadNormUseCase.Query(announcement.getEli()));
    return ResponseEntity.ok(NormResponseMapper.fromUseCaseData(norm));
  }
}
