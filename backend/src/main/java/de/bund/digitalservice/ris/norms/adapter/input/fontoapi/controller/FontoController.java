package de.bund.digitalservice.ris.norms.adapter.input.fontoapi.controller;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

import de.bund.digitalservice.ris.norms.adapter.input.fontoapi.schema.DocumentSchema;
import de.bund.digitalservice.ris.norms.adapter.input.fontoapi.schema.LockSchema;
import de.bund.digitalservice.ris.norms.adapter.input.fontoapi.schema.PutDocumentBodySchema;
import de.bund.digitalservice.ris.norms.application.port.input.LoadNormUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.LoadRegelungstextXmlUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.UpdateRegelungstextXmlUseCase;
import de.bund.digitalservice.ris.norms.domain.entity.BinaryFile;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentExpressionEli;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/fonto")
public class FontoController {

  private final LoadRegelungstextXmlUseCase loadRegelungstextXmlUseCase;
  private final UpdateRegelungstextXmlUseCase updateRegelungstextXmlUseCase;
  private final LoadNormUseCase loadNormUseCase;

  public FontoController(
    LoadRegelungstextXmlUseCase loadRegelungstextXmlUseCase,
    UpdateRegelungstextXmlUseCase updateRegelungstextXmlUseCase,
    LoadNormUseCase loadNormUseCase,
    LoadNormUseCase loadNormUseCase1
  ) {
    this.loadRegelungstextXmlUseCase = loadRegelungstextXmlUseCase;
    this.updateRegelungstextXmlUseCase = updateRegelungstextXmlUseCase;
    this.loadNormUseCase = loadNormUseCase1;
  }

  @GetMapping(
    path = "document",
    produces = { APPLICATION_JSON_VALUE },
    consumes = { APPLICATION_JSON_VALUE }
  )
  public ResponseEntity<DocumentSchema> getDocument(@RequestParam String documentId) {
    String xml = loadRegelungstextXmlUseCase.loadRegelungstextXml(
      new LoadRegelungstextXmlUseCase.Options(DokumentExpressionEli.fromString(documentId))
    );

    return ResponseEntity.ok(new DocumentSchema(documentId, xml, new LockSchema(true, true)));
  }

  @PutMapping(
    path = "document",
    produces = { APPLICATION_JSON_VALUE },
    consumes = { APPLICATION_JSON_VALUE }
  )
  public HttpStatus putDocument(@RequestBody PutDocumentBodySchema putDocumentBodySchema) {
    updateRegelungstextXmlUseCase.updateRegelungstextXml(
      new UpdateRegelungstextXmlUseCase.Options(
        DokumentExpressionEli.fromString(putDocumentBodySchema.documentId()),
        putDocumentBodySchema.content()
      )
    );

    return HttpStatus.NO_CONTENT;
  }

  @PutMapping(
    path = "document/lock",
    produces = { APPLICATION_JSON_VALUE },
    consumes = { APPLICATION_JSON_VALUE }
  )
  public HttpStatus putDocumentLock() {
    return HttpStatus.NO_CONTENT;
  }

  @GetMapping(path = "asset/preview")
  public ResponseEntity<byte[]> getAssetPreview(@RequestParam String id) {
    var eli = DokumentExpressionEli.fromString(id);

    var norm = loadNormUseCase.loadNorm(new LoadNormUseCase.EliOptions(eli.asNormEli()));

    var fileContent = norm
      .getBinaryFiles()
      .stream()
      .filter(binaryFile -> binaryFile.getDokumentManifestationEli().asExpressionEli().equals(eli))
      .map(BinaryFile::getContent)
      .findFirst();

    return ResponseEntity.of(fileContent);
  }
}
