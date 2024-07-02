package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper.ProprietaryResponseMapper;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.ProprietarySchema;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.ProprietarySingleElementSchema;
import de.bund.digitalservice.ris.norms.application.port.input.LoadProprietaryFromNormUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.UpdateProprietaryFromNormUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.UpdateProprietarySingleElementFromNormUseCase;
import de.bund.digitalservice.ris.norms.domain.entity.Eli;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.Proprietary;
import java.time.LocalDate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Retrieve proprietary data of a {@link Norm}. */
@RestController
@RequestMapping(
    "/api/v1/norms/eli/bund/{agent}/{year}/{naturalIdentifier}/{pointInTime}/{version}/{language}/{subtype}/proprietary")
public class ProprietaryController {

  private final LoadProprietaryFromNormUseCase loadProprietaryFromNormUseCase;
  private final UpdateProprietaryFromNormUseCase updateProprietaryFromNormUseCase;
  private final UpdateProprietarySingleElementFromNormUseCase
      updateProprietarySingleElementFromNormUseCase;

  public ProprietaryController(
      LoadProprietaryFromNormUseCase loadProprietaryFromNormUseCase,
      UpdateProprietaryFromNormUseCase updateProprietaryFromNormUseCase,
      UpdateProprietarySingleElementFromNormUseCase updateProprietarySingleElementFromNormUseCase) {
    this.loadProprietaryFromNormUseCase = loadProprietaryFromNormUseCase;
    this.updateProprietaryFromNormUseCase = updateProprietaryFromNormUseCase;
    this.updateProprietarySingleElementFromNormUseCase =
        updateProprietarySingleElementFromNormUseCase;
  }

  /**
   * Return specific metadata of the {@link Norm} at a given date within the {@link Proprietary}
   * node.
   *
   * @param eli Eli of the ZF0 version of the target law where the metadata are saved.
   * @param atDate the time boundary at which to return the metadata
   * @return the specific metadata returned in the form of {@link ProprietarySchema}
   */
  @GetMapping(
      path = "/{atDate}",
      produces = {APPLICATION_JSON_VALUE})
  public ResponseEntity<ProprietarySchema> getProprietaryAtDate(
      final Eli eli, @PathVariable final LocalDate atDate) {

    var proprietary =
        loadProprietaryFromNormUseCase.loadProprietaryFromNorm(
            new LoadProprietaryFromNormUseCase.Query(eli.getValue()));

    return ResponseEntity.ok(ProprietaryResponseMapper.fromProprietary(proprietary, atDate));
  }

  /**
   * Updates specific metadata of the {@link Norm} at a given date that are present within the
   * {@link Proprietary} node.
   *
   * @param eli Eli of the ZF0 version of the target law where the metadata are saved.
   * @param atDate the time boundary at which to update the metadata.
   * @param proprietarySchema the request {@link ProprietarySchema} with the new metadata values.
   * @return the specific metadata updated in the form of {@link ProprietarySchema}
   */
  @PutMapping(
      path = "/{atDate}",
      consumes = {APPLICATION_JSON_VALUE},
      produces = {APPLICATION_JSON_VALUE})
  public ResponseEntity<ProprietarySchema> updateProprietaryAtDate(
      final Eli eli,
      @PathVariable final LocalDate atDate,
      @RequestBody ProprietarySchema proprietarySchema) {

    var proprietary =
        updateProprietaryFromNormUseCase.updateProprietaryFrameFromNorm(
            new UpdateProprietaryFromNormUseCase.Query(
                eli.getValue(),
                atDate,
                new UpdateProprietaryFromNormUseCase.Metadata(
                    proprietarySchema.getFna(),
                    proprietarySchema.getArt(),
                    proprietarySchema.getTyp(),
                    proprietarySchema.getSubtyp(),
                    proprietarySchema.getBezeichnungInVorlage(),
                    proprietarySchema.getArtDerNorm(),
                    proprietarySchema.getNormgeber(),
                    proprietarySchema.getBeschliessendesOrgan(),
                    proprietarySchema.getQualifizierteMehrheit(),
                    proprietarySchema.getFederfuehrung(),
                    proprietarySchema.getOrganisationsEinheit())));

    return ResponseEntity.ok(ProprietaryResponseMapper.fromProprietary(proprietary, atDate));
  }

  /**
   * Return specific metadata of a single element at a given date within the {@link Proprietary}
   * node.
   *
   * @param eli Eli of the ZF0 version of the target law where the metadata are saved.
   * @param eid the eId of the single element within the ZF0
   * @param atDate the time boundary at which to return the metadata
   * @return the specific metadata returned in the form of {@link ProprietarySingleElementSchema}
   */
  @GetMapping(
      path = "/{eid}/{atDate}",
      produces = {APPLICATION_JSON_VALUE})
  public ResponseEntity<ProprietarySingleElementSchema> getProprietaryAtDate(
      final Eli eli, @PathVariable final String eid, @PathVariable final LocalDate atDate) {

    var proprietary =
        loadProprietaryFromNormUseCase.loadProprietaryFromNorm(
            new LoadProprietaryFromNormUseCase.Query(eli.getValue()));

    return ResponseEntity.ok(
        ProprietaryResponseMapper.fromProprietarySingleElement(proprietary, eid, atDate));
  }

  /**
   * Updates specific metadata of a single element at a given date within the {@link Proprietary}
   * node.
   *
   * @param eli Eli of the ZF0 version of the target law where the metadata are saved
   * @param eid the eId of the single element within the ZF0
   * @param atDate the time boundary at which to return the metadata
   * @param proprietarySchema the request {@link ProprietarySingleElementSchema} with the new
   *     metadata values.
   * @return the specific metadata returned in the form of {@link ProprietarySingleElementSchema}
   */
  @PutMapping(
      path = "/{eid}/{atDate}",
      consumes = {APPLICATION_JSON_VALUE},
      produces = {APPLICATION_JSON_VALUE})
  public ResponseEntity<ProprietarySingleElementSchema> updateProprietaryAtDate(
      final Eli eli,
      @PathVariable final String eid,
      @PathVariable final LocalDate atDate,
      @RequestBody ProprietarySingleElementSchema proprietarySchema) {

    var proprietary =
        updateProprietarySingleElementFromNormUseCase.updateProprietarySingleElementFromNorm(
            new UpdateProprietarySingleElementFromNormUseCase.Query(
                eli.getValue(),
                eid,
                atDate,
                new UpdateProprietarySingleElementFromNormUseCase.Metadata(
                    proprietarySchema.getArtDerNorm())));

    return ResponseEntity.ok(
        ProprietaryResponseMapper.fromProprietarySingleElement(proprietary, eid, atDate));
  }
}
