package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.springframework.http.MediaType.*;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper.NormResponseMapper;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper.ReleaseResponseMapper;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.NormResponseSchema;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.ReleaseResponseSchema;
import de.bund.digitalservice.ris.norms.application.port.input.LoadAllAnnouncementsUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadAnnouncementUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadTargetNormsAffectedByAnnouncementUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.ReleaseAnnouncementUseCase;
import de.bund.digitalservice.ris.norms.domain.entity.Announcement;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Controller for announcement-related actions. */
@RestController
@RequestMapping("/api/v1/announcements")
public class AnnouncementController {

  private final LoadAllAnnouncementsUseCase loadAllAnnouncementsUseCase;
  private final LoadAnnouncementUseCase loadAnnouncementUseCase;
  private final LoadTargetNormsAffectedByAnnouncementUseCase
      loadTargetNormsAffectedByAnnouncementUseCase;
  private final ReleaseAnnouncementUseCase releaseAnnouncementUseCase;

  public AnnouncementController(
      LoadAllAnnouncementsUseCase loadAllAnnouncementsUseCase,
      LoadAnnouncementUseCase loadAnnouncementUseCase,
      LoadTargetNormsAffectedByAnnouncementUseCase loadTargetNormsAffectedByAnnouncementUseCase,
      ReleaseAnnouncementUseCase releaseAnnouncementUseCase) {
    this.loadAllAnnouncementsUseCase = loadAllAnnouncementsUseCase;
    this.loadAnnouncementUseCase = loadAnnouncementUseCase;
    this.loadTargetNormsAffectedByAnnouncementUseCase =
        loadTargetNormsAffectedByAnnouncementUseCase;
    this.releaseAnnouncementUseCase = releaseAnnouncementUseCase;
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
    var responseSchemas =
        loadAllAnnouncementsUseCase.loadAllAnnouncements().stream()
            .map(Announcement::getNorm)
            .map(NormResponseMapper::fromUseCaseData)
            .toList();
    return ResponseEntity.ok(responseSchemas);
  }

  /**
   * Retrieves a release of an {@link Announcement} based on its {@link Norm}'s expression ELI. The
   * ELI's components are interpreted as query parameters.
   *
   * <p>(German terms are taken from the LDML_de 1.6 specs, p146/147, cf. <a
   * href="https://github.com/digitalservicebund/ris-norms/commit/17778285381a674f1a2b742ed573b7d3d542ea24">...</a>)
   *
   * @param agent DE: "Verkündungsblatt"
   * @param year DE "Verkündungsjahr"
   * @param naturalIdentifier DE: "Seitenzahl / Verkündungsnummer"
   * @param pointInTime DE: "Versionsdatum"
   * @param version DE: "Versionsnummer"
   * @param language DE: "Sprache"
   * @param subtype DE: "Dokumentenart"
   * @return A {@link ResponseEntity} containing the retrieved release.
   *     <p>Returns HTTP 200 (OK) and the release if found.
   *     <p>Returns HTTP 404 (Not Found) if no release is found.
   */
  @GetMapping(
      path =
          "/eli/bund/{agent}/{year}/{naturalIdentifier}/{pointInTime}/{version}/{language}/{subtype}/release",
      produces = {APPLICATION_JSON_VALUE})
  public ResponseEntity<ReleaseResponseSchema> getRelease(
      @PathVariable final String agent,
      @PathVariable final String year,
      @PathVariable final String naturalIdentifier,
      @PathVariable final String pointInTime,
      @PathVariable final String version,
      @PathVariable final String language,
      @PathVariable final String subtype) {
    final String eli =
        buildEli(agent, year, naturalIdentifier, pointInTime, version, language, subtype);

    var affectedNorms =
        loadTargetNormsAffectedByAnnouncementUseCase.loadTargetNormsAffectedByAnnouncement(
            new LoadTargetNormsAffectedByAnnouncementUseCase.Query(eli));

    return loadAnnouncementUseCase
        .loadAnnouncement(new LoadAnnouncementUseCase.Query(eli))
        .map(announcement -> ReleaseResponseMapper.fromAnnouncement(announcement, affectedNorms))
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  /**
   * Releases an {@link Announcement} (and all related {@link Norm}'s) based on its {@link Norm}'s
   * expression ELI. The ELI's components are interpreted as query parameters.
   *
   * <p>(German terms are taken from the LDML_de 1.6 specs, p146/147, cf. <a
   * href="https://github.com/digitalservicebund/ris-norms/commit/17778285381a674f1a2b742ed573b7d3d542ea24">...</a>)
   *
   * @param agent DE: "Verkündungsblatt"
   * @param year DE "Verkündungsjahr"
   * @param naturalIdentifier DE: "Seitenzahl / Verkündungsnummer"
   * @param pointInTime DE: "Versionsdatum"
   * @param version DE: "Versionsnummer"
   * @param language DE: "Sprache"
   * @param subtype DE: "Dokumentenart"
   * @return A {@link ResponseEntity} containing the created release.
   *     <p>Returns HTTP 200 (OK) and the release was successful.
   *     <p>Returns HTTP 404 (Not Found) if no {@link Announcement} is found.
   */
  @PutMapping(
      path =
          "/eli/bund/{agent}/{year}/{naturalIdentifier}/{pointInTime}/{version}/{language}/{subtype}/release",
      produces = {APPLICATION_JSON_VALUE})
  public ResponseEntity<ReleaseResponseSchema> putRelease(
      @PathVariable final String agent,
      @PathVariable final String year,
      @PathVariable final String naturalIdentifier,
      @PathVariable final String pointInTime,
      @PathVariable final String version,
      @PathVariable final String language,
      @PathVariable final String subtype) {
    final String eli =
        buildEli(agent, year, naturalIdentifier, pointInTime, version, language, subtype);

    var announcementOptional =
        releaseAnnouncementUseCase.releaseAnnouncement(new ReleaseAnnouncementUseCase.Query(eli));

    var affectedNorms =
        loadTargetNormsAffectedByAnnouncementUseCase.loadTargetNormsAffectedByAnnouncement(
            new LoadTargetNormsAffectedByAnnouncementUseCase.Query(eli));

    return announcementOptional
        .map(announcement -> ReleaseResponseMapper.fromAnnouncement(announcement, affectedNorms))
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @NotNull
  private static String buildEli(
      String agent,
      String year,
      String naturalIdentifier,
      String pointInTime,
      String version,
      String language,
      String subtype) {
    return "eli/bund/"
        + agent
        + "/"
        + year
        + "/"
        + naturalIdentifier
        + "/"
        + pointInTime
        + "/"
        + version
        + "/"
        + language
        + "/"
        + subtype;
  }
}
