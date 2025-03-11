package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.exception.DokumentNotFoundException;
import de.bund.digitalservice.ris.norms.application.port.input.LoadProprietaryFromDokumentUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.UpdateProprietaryFrameFromDokumentUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.UpdateProprietarySingleElementFromDokumentUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.LoadDokumentPort;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateDokumentPort;
import de.bund.digitalservice.ris.norms.domain.entity.Dokument;
import de.bund.digitalservice.ris.norms.domain.entity.EId;
import de.bund.digitalservice.ris.norms.domain.entity.Metadata;
import de.bund.digitalservice.ris.norms.domain.entity.Proprietary;
import de.bund.digitalservice.ris.norms.utils.EidConsistencyGuardian;
import org.springframework.stereotype.Service;

/** Implements operations related to the "proprietary" of a {@link Dokument} */
@Service
public class ProprietaryService
  implements
    LoadProprietaryFromDokumentUseCase,
    UpdateProprietaryFrameFromDokumentUseCase,
    UpdateProprietarySingleElementFromDokumentUseCase {

  final LoadDokumentPort loadDokumentPort;
  final UpdateDokumentPort updateDokumentPort;

  ProprietaryService(LoadDokumentPort loadDokumentPort, UpdateDokumentPort updateDokumentPort) {
    this.loadDokumentPort = loadDokumentPort;
    this.updateDokumentPort = updateDokumentPort;
  }

  @Override
  public Proprietary loadProprietaryFromDokument(LoadProprietaryFromDokumentUseCase.Query query) {
    return loadDokumentPort
      .loadDokument(new LoadDokumentPort.Command(query.dokumentExpressionEli()))
      .map(m -> m.getMeta().getOrCreateProprietary())
      .orElseThrow(() -> new DokumentNotFoundException(query.dokumentExpressionEli().toString()));
  }

  @Override
  public Proprietary updateProprietaryFrameFromDokument(
    UpdateProprietaryFrameFromDokumentUseCase.Query query
  ) {
    final Dokument dokument = loadDokumentPort
      .loadDokument(new LoadDokumentPort.Command(query.dokumentExpressionEli()))
      .orElseThrow(() -> new DokumentNotFoundException(query.dokumentExpressionEli().toString()));

    final Proprietary proprietary = dokument.getMeta().getOrCreateProprietary();
    proprietary.setMetadataValue(Metadata.FNA, query.inputMetadata().fna());
    proprietary.setMetadataValue(Metadata.ART, query.inputMetadata().art());
    proprietary.setMetadataValue(Metadata.TYP, query.inputMetadata().typ());
    proprietary.setMetadataValue(Metadata.SUBTYP, query.inputMetadata().subtyp());
    proprietary.setMetadataValue(
      Metadata.BEZEICHNUNG_IN_VORLAGE,
      query.inputMetadata().bezeichnungInVorlage()
    );
    proprietary.setMetadataValue(Metadata.ART_DER_NORM, query.inputMetadata().artDerNorm());
    proprietary.setMetadataValue(Metadata.STAAT, query.inputMetadata().staat());
    proprietary.setMetadataValue(
      Metadata.BESCHLIESSENDES_ORGAN,
      query.inputMetadata().beschliessendesOrgan()
    );
    proprietary.setMetadataValue(
      Metadata.BESCHLIESSENDES_ORGAN_QUALMEHR,
      query.inputMetadata().qualifizierterMehrheit() != null
        ? query.inputMetadata().qualifizierterMehrheit().toString()
        : ""
    );
    proprietary.setRessort(
      query.inputMetadata().ressort(),
      query.dokumentExpressionEli().getPointInTime()
    );
    proprietary.setMetadataValue(
      Metadata.ORGANISATIONS_EINHEIT,
      query.inputMetadata().organisationsEinheit()
    );
    return updateDokument(dokument);
  }

  @Override
  public Proprietary updateProprietarySingleElementFromDokument(
    UpdateProprietarySingleElementFromDokumentUseCase.Query query
  ) {
    final Dokument dokument = loadDokumentPort
      .loadDokument(new LoadDokumentPort.Command(query.dokumentExpressionEli()))
      .orElseThrow(() -> new DokumentNotFoundException(query.dokumentExpressionEli().toString()));
    final Proprietary proprietary = dokument.getMeta().getOrCreateProprietary();
    proprietary.setMetadataValue(
      Metadata.ART_DER_NORM,
      new EId(query.eid()),
      query.inputMetadata().artDerNorm()
    );
    return updateDokument(dokument);
  }

  private Proprietary updateDokument(final Dokument dokument) {
    EidConsistencyGuardian.eliminateDeadReferences(dokument.getDocument());
    EidConsistencyGuardian.correctEids(dokument.getDocument());
    return updateDokumentPort
      .updateDokument(new UpdateDokumentPort.Command(dokument))
      .map(updated -> updated.getMeta().getOrCreateProprietary())
      .orElse(null);
  }
}
