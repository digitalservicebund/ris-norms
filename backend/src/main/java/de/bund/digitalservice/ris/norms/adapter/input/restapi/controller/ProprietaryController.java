package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper.ProprietaryResponseMapper;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.ProprietaryFrameSchema;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.ProprietarySingleElementSchema;
import de.bund.digitalservice.ris.norms.application.port.input.LoadProprietaryFromDokumentUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.UpdateProprietaryFrameFromDokumentUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.UpdateProprietarySingleElementFromDokumentUseCase;
import de.bund.digitalservice.ris.norms.domain.entity.Dokument;
import de.bund.digitalservice.ris.norms.domain.entity.EId;
import de.bund.digitalservice.ris.norms.domain.entity.Proprietary;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentExpressionEli;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Retrieve proprietary data of a {@link Dokument}. */
@RestController
@RequestMapping(
  "/api/v1/norms/eli/bund/{agent}/{year}/{naturalIdentifier}/{pointInTime}/{version}/{language}/{subtype}/proprietary"
)
public class ProprietaryController {

  private final LoadProprietaryFromDokumentUseCase loadProprietaryFromDokumentUseCase;
  private final UpdateProprietaryFrameFromDokumentUseCase updateProprietaryFrameFromDokumentUseCase;
  private final UpdateProprietarySingleElementFromDokumentUseCase updateProprietarySingleElementFromDokumentUseCase;

  public ProprietaryController(
    LoadProprietaryFromDokumentUseCase loadProprietaryFromDokumentUseCase,
    UpdateProprietaryFrameFromDokumentUseCase updateProprietaryFrameFromDokumentUseCase,
    UpdateProprietarySingleElementFromDokumentUseCase updateProprietarySingleElementFromDokumentUseCase
  ) {
    this.loadProprietaryFromDokumentUseCase = loadProprietaryFromDokumentUseCase;
    this.updateProprietaryFrameFromDokumentUseCase = updateProprietaryFrameFromDokumentUseCase;
    this.updateProprietarySingleElementFromDokumentUseCase =
      updateProprietarySingleElementFromDokumentUseCase;
  }

  /**
   * Return specific metadata of the {@link Dokument} within the {@link Proprietary} node.
   *
   * @param dokumentExpressionEli the eli at the document level
   * @return the specific metadata returned in the form of {@link ProprietaryFrameSchema}
   */
  @GetMapping(produces = { APPLICATION_JSON_VALUE })
  public ResponseEntity<ProprietaryFrameSchema> getProprietary(
    final DokumentExpressionEli dokumentExpressionEli
  ) {
    var proprietary = loadProprietaryFromDokumentUseCase.loadProprietaryFromDokument(
      new LoadProprietaryFromDokumentUseCase.Query(dokumentExpressionEli)
    );

    return ResponseEntity.ok(
      ProprietaryResponseMapper.fromProprietary(proprietary, dokumentExpressionEli.getPointInTime())
    );
  }

  /**
   * Updates specific metadata of the {@link Dokument} within the
   * {@link Proprietary} node.
   *
   * @param dokumentExpressionEli the eli at the document level
   * @param proprietaryFrameSchema the request {@link ProprietaryFrameSchema} with the new metadata
   *     values.
   * @return the specific metadata updated in the form of {@link ProprietaryFrameSchema}
   */
  @PutMapping(consumes = { APPLICATION_JSON_VALUE }, produces = { APPLICATION_JSON_VALUE })
  public ResponseEntity<ProprietaryFrameSchema> updateProprietary(
    final DokumentExpressionEli dokumentExpressionEli,
    @RequestBody ProprietaryFrameSchema proprietaryFrameSchema
  ) {
    var proprietary = updateProprietaryFrameFromDokumentUseCase.updateProprietaryFrameFromDokument(
      new UpdateProprietaryFrameFromDokumentUseCase.Query(
        dokumentExpressionEli,
        new UpdateProprietaryFrameFromDokumentUseCase.InputMetadata(
          proprietaryFrameSchema.getFna(),
          proprietaryFrameSchema.getArt(),
          proprietaryFrameSchema.getTyp(),
          proprietaryFrameSchema.getSubtyp(),
          proprietaryFrameSchema.getBezeichnungInVorlage(),
          proprietaryFrameSchema.getArtDerNorm(),
          proprietaryFrameSchema.getStaat(),
          proprietaryFrameSchema.getBeschliessendesOrgan(),
          proprietaryFrameSchema.getQualifizierteMehrheit(),
          proprietaryFrameSchema.getRessort(),
          proprietaryFrameSchema.getOrganisationsEinheit()
        )
      )
    );

    return ResponseEntity.ok(
      ProprietaryResponseMapper.fromProprietary(proprietary, dokumentExpressionEli.getPointInTime())
    );
  }

  /**
   * Return specific metadata of a single element at a given date within the {@link Proprietary}
   * node.
   *
   * @param dokumentExpressionEli the eli at the document level
   * @param eid the eId of the single element within the {@link Dokument}
   * @return the specific metadata returned in the form of {@link ProprietarySingleElementSchema}
   */
  @GetMapping(path = "/{eid}", produces = { APPLICATION_JSON_VALUE })
  public ResponseEntity<ProprietarySingleElementSchema> getProprietarySingleElement(
    final DokumentExpressionEli dokumentExpressionEli,
    @PathVariable final EId eid
  ) {
    var proprietary = loadProprietaryFromDokumentUseCase.loadProprietaryFromDokument(
      new LoadProprietaryFromDokumentUseCase.Query(dokumentExpressionEli)
    );

    return ResponseEntity.ok(
      ProprietaryResponseMapper.fromProprietarySingleElement(proprietary, eid)
    );
  }

  /**
   * Updates specific metadata of a single element at a given date within the {@link Proprietary}
   * node.
   *
   * @param dokumentExpressionEli the eli at the document level
   * @param eid the eId of the single element within the {@link Dokument}
   * @param proprietarySchema the request {@link ProprietarySingleElementSchema} with the new
   *     metadata values.
   * @return the specific metadata returned in the form of {@link ProprietarySingleElementSchema}
   */
  @PutMapping(
    path = "/{eid}",
    consumes = { APPLICATION_JSON_VALUE },
    produces = { APPLICATION_JSON_VALUE }
  )
  public ResponseEntity<ProprietarySingleElementSchema> updateProprietarySingleElement(
    final DokumentExpressionEli dokumentExpressionEli,
    @PathVariable final EId eid,
    @RequestBody ProprietarySingleElementSchema proprietarySchema
  ) {
    var proprietary =
      updateProprietarySingleElementFromDokumentUseCase.updateProprietarySingleElementFromDokument(
        new UpdateProprietarySingleElementFromDokumentUseCase.Query(
          dokumentExpressionEli,
          eid,
          new UpdateProprietarySingleElementFromDokumentUseCase.InputMetadata(
            proprietarySchema.getArtDerNorm()
          )
        )
      );

    return ResponseEntity.ok(
      ProprietaryResponseMapper.fromProprietarySingleElement(proprietary, eid)
    );
  }
}
