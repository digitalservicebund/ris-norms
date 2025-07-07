package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import de.bund.digitalservice.ris.norms.adapter.input.restapi.mapper.ProprietaryResponseMapper;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.ProprietaryFrameSchema;
import de.bund.digitalservice.ris.norms.adapter.input.restapi.schema.ProprietarySingleElementSchema;
import de.bund.digitalservice.ris.norms.application.port.input.LoadNormUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadProprietaryFromDokumentUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.UpdateProprietarySingleElementFromDokumentUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.UpdateRahmenMetadataUseCase;
import de.bund.digitalservice.ris.norms.domain.entity.Dokument;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.Proprietary;
import de.bund.digitalservice.ris.norms.domain.entity.eid.EId;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentExpressionEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
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
  private final UpdateRahmenMetadataUseCase updateRahmenMetadataUseCase;
  private final UpdateProprietarySingleElementFromDokumentUseCase updateProprietarySingleElementFromDokumentUseCase;
  private final LoadNormUseCase loadNormUseCase;

  public ProprietaryController(
    LoadProprietaryFromDokumentUseCase loadProprietaryFromDokumentUseCase,
    UpdateRahmenMetadataUseCase updateRahmenMetadataUseCase,
    UpdateProprietarySingleElementFromDokumentUseCase updateProprietarySingleElementFromDokumentUseCase,
    LoadNormUseCase loadNormUseCase
  ) {
    this.loadProprietaryFromDokumentUseCase = loadProprietaryFromDokumentUseCase;
    this.updateRahmenMetadataUseCase = updateRahmenMetadataUseCase;
    this.updateProprietarySingleElementFromDokumentUseCase =
      updateProprietarySingleElementFromDokumentUseCase;
    this.loadNormUseCase = loadNormUseCase;
  }

  /**
   * Return rahmen metadata of the {@link Norm}.
   *
   * @param normExpressionEli the eli of the norm
   * @return the rahmen metadata
   */
  @GetMapping(produces = { APPLICATION_JSON_VALUE })
  public ResponseEntity<ProprietaryFrameSchema> getProprietary(
    final NormExpressionEli normExpressionEli
  ) {
    var norm = loadNormUseCase.loadNorm(new LoadNormUseCase.EliOptions(normExpressionEli));

    return ResponseEntity.ok(
      ProprietaryResponseMapper.fromRahmenMetadata(norm.getRahmenMetadata())
    );
  }

  /**
   * Updates rahmen metadata of the {@link Norm}.
   *
   * @param normExpressionEli the eli identifying the norm
   * @param proprietaryFrameSchema the request {@link ProprietaryFrameSchema} with the new metadata
   *     values.
   * @return the specific metadata updated in the form of {@link ProprietaryFrameSchema}
   */
  @PutMapping(consumes = { APPLICATION_JSON_VALUE }, produces = { APPLICATION_JSON_VALUE })
  public ResponseEntity<ProprietaryFrameSchema> updateProprietary(
    final NormExpressionEli normExpressionEli,
    @RequestBody ProprietaryFrameSchema proprietaryFrameSchema
  ) {
    var rahmenMetadata = updateRahmenMetadataUseCase.updateRahmenMetadata(
      new UpdateRahmenMetadataUseCase.Options(
        normExpressionEli,
        new UpdateRahmenMetadataUseCase.InputMetadata(
          proprietaryFrameSchema.getFna(),
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

    return ResponseEntity.ok(ProprietaryResponseMapper.fromRahmenMetadata(rahmenMetadata));
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
      new LoadProprietaryFromDokumentUseCase.Options(dokumentExpressionEli)
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
        new UpdateProprietarySingleElementFromDokumentUseCase.Options(
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
