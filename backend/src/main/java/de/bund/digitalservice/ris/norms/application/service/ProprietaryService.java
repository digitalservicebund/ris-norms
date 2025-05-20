package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.exception.DokumentNotFoundException;
import de.bund.digitalservice.ris.norms.application.port.input.LoadProprietaryFromDokumentUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.UpdateProprietaryFrameFromDokumentUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.UpdateProprietarySingleElementFromDokumentUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.LoadDokumentPort;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateDokumentPort;
import de.bund.digitalservice.ris.norms.domain.entity.Dokument;
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
  public Proprietary loadProprietaryFromDokument(
    LoadProprietaryFromDokumentUseCase.Options options
  ) {
    return loadDokumentPort
      .loadDokument(new LoadDokumentPort.Command(options.dokumentExpressionEli()))
      .map(m -> m.getMeta().getOrCreateProprietary())
      .orElseThrow(() -> new DokumentNotFoundException(options.dokumentExpressionEli().toString()));
  }

  @Override
  public Proprietary updateProprietaryFrameFromDokument(
    UpdateProprietaryFrameFromDokumentUseCase.Options options
  ) {
    final Dokument dokument = loadDokumentPort
      .loadDokument(new LoadDokumentPort.Command(options.dokumentExpressionEli()))
      .orElseThrow(() -> new DokumentNotFoundException(options.dokumentExpressionEli().toString()));

    final Proprietary proprietary = dokument.getMeta().getOrCreateProprietary();
    proprietary.setMetadataValue(Metadata.FNA, options.inputMetadata().fna());
    proprietary.setMetadataValue(Metadata.ART, options.inputMetadata().art());
    proprietary.setMetadataValue(Metadata.TYP, options.inputMetadata().typ());
    proprietary.setMetadataValue(Metadata.SUBTYP, options.inputMetadata().subtyp());
    proprietary.setMetadataValue(
      Metadata.BEZEICHNUNG_IN_VORLAGE,
      options.inputMetadata().bezeichnungInVorlage()
    );
    proprietary.setMetadataValue(Metadata.ART_DER_NORM, options.inputMetadata().artDerNorm());
    proprietary.setMetadataValue(Metadata.STAAT, options.inputMetadata().staat());
    proprietary.setMetadataValue(
      Metadata.BESCHLIESSENDES_ORGAN,
      options.inputMetadata().beschliessendesOrgan()
    );
    proprietary.setMetadataValue(
      Metadata.BESCHLIESSENDES_ORGAN_QUALMEHR,
      options.inputMetadata().qualifizierterMehrheit() != null
        ? options.inputMetadata().qualifizierterMehrheit().toString()
        : ""
    );
    proprietary.setRessort(
      options.inputMetadata().ressort(),
      options.dokumentExpressionEli().getPointInTime()
    );
    proprietary.setMetadataValue(
      Metadata.ORGANISATIONS_EINHEIT,
      options.inputMetadata().organisationsEinheit()
    );
    return updateDokument(dokument);
  }

  @Override
  public Proprietary updateProprietarySingleElementFromDokument(
    UpdateProprietarySingleElementFromDokumentUseCase.Options options
  ) {
    final Dokument dokument = loadDokumentPort
      .loadDokument(new LoadDokumentPort.Command(options.dokumentExpressionEli()))
      .orElseThrow(() -> new DokumentNotFoundException(options.dokumentExpressionEli().toString()));
    final Proprietary proprietary = dokument.getMeta().getOrCreateProprietary();
    proprietary.setMetadataValue(
      Metadata.ART_DER_NORM,
      options.eid(),
      options.inputMetadata().artDerNorm()
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
