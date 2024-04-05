package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.springframework.http.MediaType.*;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper.TargetLawResponseMapper;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.TargetLawResponseSchema;
import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.application.service.TimeMachineService;
import de.bund.digitalservice.ris.norms.application.service.XmlDocumentService;
import de.bund.digitalservice.ris.norms.domain.entity.TargetLaw;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.Document;

/**
 * Controller class for handling target laws in the REST API. This class is annotated with {@link
 * RestController} and {@link RequestMapping} to define the base URL for handling target laws in the
 * API.
 */
@RestController
@RequestMapping("api/v1/target-laws")
public class TargetLawController {

  private final LoadTargetLawUseCase loadTargetLawUseCase;
  private final LoadTargetLawXmlUseCase loadTargetLawXmlUseCase;
  private final UpdateTargetLawUseCase updateTargetLawUseCase;
  private final TimeMachineUseCase timeMachineUseCase;
  private final TransformLegalDocMlToHtmlUseCase transformLegalDocMlToHtmlUseCase;

  public TargetLawController(
      LoadTargetLawUseCase loadTargetLawUseCase,
      LoadTargetLawXmlUseCase loadTargetLawXmlUseCase,
      UpdateTargetLawUseCase updateTargetLawUseCase,
      TimeMachineUseCase timeMachineUseCase,
      TransformLegalDocMlToHtmlUseCase transformLegalDocMlToHtmlUseCase) {
    this.loadTargetLawUseCase = loadTargetLawUseCase;
    this.loadTargetLawXmlUseCase = loadTargetLawXmlUseCase;
    this.updateTargetLawUseCase = updateTargetLawUseCase;
    this.timeMachineUseCase = timeMachineUseCase;
    this.transformLegalDocMlToHtmlUseCase = transformLegalDocMlToHtmlUseCase;
  }

  /**
   * Retrieves a target law based on its expression ELI.
   *
   * <p>(German terms are taken from the LDML_de 1.6 specs, p146/147, cf. <a
   * href="https://github.com/digitalservicebund/ris-norms/commit/17778285381a674f1a2b742ed573b7d3d542ea24">...</a>)
   *
   * @param printAnnouncementGazette DE: "Verkündungsblatt"
   * @param printAnnouncementYear DE "Verkündungsjahr"
   * @param printAnnouncementPage DE: "Seitenzahl / Verkündungsnummer"
   * @param pointInTime DE: "Versionsdatum"
   * @param version DE: "Versionsnummer"
   * @param language DE: "Sprache"
   * @param subtype DE: "Dokumentenart"
   * @return A {@link ResponseEntity} containing the retrieved amending law.
   *     <p>Returns HTTP 200 (OK) and the target law's data if found.
   *     <p>Returns HTTP 404 (Not Found) if the target law is not found.
   */
  @GetMapping(
      path =
          "/eli/bund/{printAnnouncementGazette}/{printAnnouncementYear}/{printAnnouncementPage}/{pointInTime}/{version}/{language}/{subtype}",
      produces = {APPLICATION_JSON_VALUE})
  public ResponseEntity<TargetLawResponseSchema> getTargetLaw(
      @PathVariable final String printAnnouncementGazette,
      @PathVariable final String printAnnouncementYear,
      @PathVariable final String printAnnouncementPage,
      @PathVariable final String pointInTime,
      @PathVariable final String version,
      @PathVariable final String language,
      @PathVariable final String subtype) {
    final String eli =
        buildEli(
            printAnnouncementGazette,
            printAnnouncementYear,
            printAnnouncementPage,
            pointInTime,
            version,
            language,
            subtype);
    final Optional<TargetLaw> targetLawOptional =
        loadTargetLawUseCase.loadTargetLaw(new LoadTargetLawUseCase.Query(eli));
    return targetLawOptional
        .map(TargetLawResponseMapper::fromUseCaseData)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  /**
   * Retrieves the xml representation of a target law based on its expression ELI.
   *
   * <p>(German terms are taken from the LDML_de 1.6 specs, p146/147, cf. <a
   * href="https://github.com/digitalservicebund/ris-norms/commit/17778285381a674f1a2b742ed573b7d3d542ea24">...</a>)
   *
   * @param printAnnouncementGazette DE: "Verkündungsblatt"
   * @param printAnnouncementYear DE "Verkündungsjahr"
   * @param printAnnouncementPage DE: "Seitenzahl / Verkündungsnummer"
   * @param pointInTime DE: "Versionsdatum"
   * @param version DE: "Versionsnummer"
   * @param language DE: "Sprache"
   * @param subtype DE: "Dokumentenart"
   * @return A {@link ResponseEntity} containing the retrieved target law.
   *     <p>Returns HTTP 200 (OK) and the target law's data if found.
   *     <p>Returns HTTP 404 (Not Found) if the target law is not found.
   */
  @GetMapping(
      path =
          "/eli/bund/{printAnnouncementGazette}/{printAnnouncementYear}/{printAnnouncementPage}/{pointInTime}/{version}/{language}/{subtype}",
      produces = {APPLICATION_XML_VALUE})
  public ResponseEntity<Document> getTargetLawXml(
      @PathVariable final String printAnnouncementGazette,
      @PathVariable final String printAnnouncementYear,
      @PathVariable final String printAnnouncementPage,
      @PathVariable final String pointInTime,
      @PathVariable final String version,
      @PathVariable final String language,
      @PathVariable final String subtype) {
    final String eli =
        buildEli(
            printAnnouncementGazette,
            printAnnouncementYear,
            printAnnouncementPage,
            pointInTime,
            version,
            language,
            subtype);
    final Optional<Document> targetLawXmlOptional =
        loadTargetLawXmlUseCase.loadTargetLawXml(new LoadTargetLawXmlUseCase.Query(eli));
    return targetLawXmlOptional
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  /**
   * Retrieves the rendered html representation of a target law based on its expression ELI.
   *
   * <p>(German terms are taken from the LDML_de 1.6 specs, p146/147, cf. <a
   * href="https://github.com/digitalservicebund/ris-norms/commit/17778285381a674f1a2b742ed573b7d3d542ea24">...</a>)
   *
   * @param printAnnouncementGazette DE: "Verkündungsblatt"
   * @param printAnnouncementYear DE "Verkündungsjahr"
   * @param printAnnouncementPage DE: "Seitenzahl / Verkündungsnummer"
   * @param pointInTime DE: "Versionsdatum"
   * @param version DE: "Versionsnummer"
   * @param language DE: "Sprache"
   * @param subtype DE: "Dokumentenart"
   * @return A {@link ResponseEntity} containing the retrieved target law.
   *     <p>Returns HTTP 200 (OK) and the target law as a rendered html if found.
   *     <p>Returns HTTP 404 (Not Found) if the target law is not found.
   */
  @GetMapping(
      path =
          "/eli/bund/{printAnnouncementGazette}/{printAnnouncementYear}/{printAnnouncementPage}/{pointInTime}/{version}/{language}/{subtype}",
      produces = {TEXT_HTML_VALUE})
  public ResponseEntity<String> getTargetLawHtml(
      @PathVariable final String printAnnouncementGazette,
      @PathVariable final String printAnnouncementYear,
      @PathVariable final String printAnnouncementPage,
      @PathVariable final String pointInTime,
      @PathVariable final String version,
      @PathVariable final String language,
      @PathVariable final String subtype) {
    final String eli =
        buildEli(
            printAnnouncementGazette,
            printAnnouncementYear,
            printAnnouncementPage,
            pointInTime,
            version,
            language,
            subtype);
    final Optional<Document> targetLawXmlOptional =
        loadTargetLawXmlUseCase.loadTargetLawXml(new LoadTargetLawXmlUseCase.Query(eli));

    // TODO: this violates the call hierarchy. We could either
    // a) make the UseCase return String (which would be a shame as Document is much richer)
    // b) provide the XML utilities via some other means than a service (which seems to go against the Spring defaults)
    final TimeMachineService timeMachineService = new TimeMachineService(new XmlDocumentService());
    
    return targetLawXmlOptional
        .map(
            xml -> {
              var html =
                  this.transformLegalDocMlToHtmlUseCase.transformLegalDocMlToHtml(
                      new TransformLegalDocMlToHtmlUseCase.Query(timeMachineService.convertDocumentToString(xml), true));
              return ResponseEntity.ok(html);
            })
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  /**
   * Retrieves the xml preview of a target law after an amending law is applied.
   *
   * <p>(German terms are taken from the LDML_de 1.6 specs, p146/147, cf. <a
   * href="https://github.com/digitalservicebund/ris-norms/commit/17778285381a674f1a2b742ed573b7d3d542ea24">...</a>)
   *
   * @param printAnnouncementGazette DE: "Verkündungsblatt"
   * @param printAnnouncementYear DE "Verkündungsjahr"
   * @param printAnnouncementPage DE: "Seitenzahl / Verkündungsnummer"
   * @param pointInTime DE: "Versionsdatum"
   * @param version DE: "Versionsnummer"
   * @param language DE: "Sprache"
   * @param subtype DE: "Dokumentenart"
   * @param amendingLaw DE: Änderungsgesetz (as XML)
   * @return A {@link ResponseEntity} containing the retrieved preview.
   *     <p>Returns HTTP 400 (Bad Request) if the amending law is missing in the request.
   */
  @PostMapping(
      path =
          "/eli/bund/{printAnnouncementGazette}/{printAnnouncementYear}/{printAnnouncementPage}/{pointInTime}/{version}/{language}/{subtype}/preview",
      consumes = {APPLICATION_XML_VALUE},
      produces = {APPLICATION_XML_VALUE})
  public ResponseEntity<String> getPreview(
      @PathVariable final String printAnnouncementGazette,
      @PathVariable final String printAnnouncementYear,
      @PathVariable final String printAnnouncementPage,
      @PathVariable final String pointInTime,
      @PathVariable final String version,
      @PathVariable final String language,
      @PathVariable final String subtype,
      @RequestBody final String amendingLaw) {

    final String eli =
        buildEli(
            printAnnouncementGazette,
            printAnnouncementYear,
            printAnnouncementPage,
            pointInTime,
            version,
            language,
            subtype);

    Optional<String> targetLawPreview =
        timeMachineUseCase.applyTimeMachine(new TimeMachineUseCase.Query(eli, amendingLaw));
    return targetLawPreview
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  /**
   * Retrieves the html preview of a target law after an amending law is applied.
   *
   * <p>(German terms are taken from the LDML_de 1.6 specs, p146/147, cf. <a
   * href="https://github.com/digitalservicebund/ris-norms/commit/17778285381a674f1a2b742ed573b7d3d542ea24">...</a>)
   *
   * @param printAnnouncementGazette DE: "Verkündungsblatt"
   * @param printAnnouncementYear DE "Verkündungsjahr"
   * @param printAnnouncementPage DE: "Seitenzahl / Verkündungsnummer"
   * @param pointInTime DE: "Versionsdatum"
   * @param version DE: "Versionsnummer"
   * @param language DE: "Sprache"
   * @param subtype DE: "Dokumentenart"
   * @param amendingLaw DE: Änderungsgesetz (as XML)
   * @return A {@link ResponseEntity} containing the retrieved preview.
   *     <p>Returns HTTP 400 (Bad Request) if the amending law is missing in the request.
   */
  @PostMapping(
      path =
          "/eli/bund/{printAnnouncementGazette}/{printAnnouncementYear}/{printAnnouncementPage}/{pointInTime}/{version}/{language}/{subtype}/preview",
      consumes = {APPLICATION_XML_VALUE},
      produces = {TEXT_HTML_VALUE})
  public ResponseEntity<String> getHtmlPreview(
      @PathVariable final String printAnnouncementGazette,
      @PathVariable final String printAnnouncementYear,
      @PathVariable final String printAnnouncementPage,
      @PathVariable final String pointInTime,
      @PathVariable final String version,
      @PathVariable final String language,
      @PathVariable final String subtype,
      @RequestBody final String amendingLaw) {
    final String eli =
        buildEli(
            printAnnouncementGazette,
            printAnnouncementYear,
            printAnnouncementPage,
            pointInTime,
            version,
            language,
            subtype);

    return timeMachineUseCase
        .applyTimeMachine(new TimeMachineUseCase.Query(eli, amendingLaw))
        .map(
            xml -> {
              var html =
                  this.transformLegalDocMlToHtmlUseCase.transformLegalDocMlToHtml(
                      new TransformLegalDocMlToHtmlUseCase.Query(xml, false));
              return ResponseEntity.ok(html);
            })
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  /**
   * Updates a target law.
   *
   * <p>(German terms are taken from the LDML_de 1.6 specs, p146/147, cf. <a
   * href="https://github.com/digitalservicebund/ris-norms/commit/17778285381a674f1a2b742ed573b7d3d542ea24">...</a>)
   *
   * @param printAnnouncementGazette DE: "Verkündungsblatt"
   * @param printAnnouncementYear DE "Verkündungsjahr"
   * @param printAnnouncementPage DE: "Seitenzahl / Verkündungsnummer"
   * @param pointInTime DE: "Versionsdatum"
   * @param version DE: "Versionsnummer"
   * @param language DE: "Sprache"
   * @param subtype DE: "Dokumentenart"
   * @param targetLaw DE: Zielgesetz (as XML)
   * @return A {@link ResponseEntity} containing the retrieved preview.
   *     <p>Returns HTTP 404 (Bad Request) if there is no target law with the corresponding ELI
   *     found.
   */
  @PutMapping(
      path =
          "/eli/bund/{printAnnouncementGazette}/{printAnnouncementYear}/{printAnnouncementPage}/{pointInTime}/{version}/{language}/{subtype}",
      consumes = {APPLICATION_XML_VALUE},
      produces = {APPLICATION_XML_VALUE})
  public ResponseEntity<Document> updateTargetLaw(
      @PathVariable final String printAnnouncementGazette,
      @PathVariable final String printAnnouncementYear,
      @PathVariable final String printAnnouncementPage,
      @PathVariable final String pointInTime,
      @PathVariable final String version,
      @PathVariable final String language,
      @PathVariable final String subtype,
      @RequestBody final String targetLaw) {

    final String eli =
        buildEli(
            printAnnouncementGazette,
            printAnnouncementYear,
            printAnnouncementPage,
            pointInTime,
            version,
            language,
            subtype);

    final TimeMachineService timeMachineService = new TimeMachineService(new XmlDocumentService());

    Optional<Document> updatedTargetLaw =
        updateTargetLawUseCase.updateTargetLaw(new UpdateTargetLawUseCase.Query(eli, timeMachineService.stringToXmlDocument(targetLaw)));

    return updatedTargetLaw
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @NotNull
  private static String buildEli(
      String printAnnouncementGazette,
      String printAnnouncementYear,
      String printAnnouncementPage,
      String pointInTime,
      String version,
      String language,
      String subtype) {
    return "eli/bund/"
        + printAnnouncementGazette
        + "/"
        + printAnnouncementYear
        + "/"
        + printAnnouncementPage
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
