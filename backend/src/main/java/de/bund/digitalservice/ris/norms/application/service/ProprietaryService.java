package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.exception.DokumentNotFoundException;
import de.bund.digitalservice.ris.norms.application.exception.NormNotFoundException;
import de.bund.digitalservice.ris.norms.application.port.input.LoadProprietaryFromDokumentUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.UpdateProprietaryFrameFromDokumentUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.UpdateProprietarySingleElementFromDokumentUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.LoadDokumentPort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.Dokument;
import de.bund.digitalservice.ris.norms.domain.entity.Metadata;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.Proprietary;
import de.bund.digitalservice.ris.norms.domain.entity.metadata.rahmen.RahmenMetadata;
import org.springframework.stereotype.Service;

/** Implements operations related to the "proprietary" of a {@link Dokument} */
@Service
public class ProprietaryService
  implements
    LoadProprietaryFromDokumentUseCase,
    UpdateProprietaryFrameFromDokumentUseCase,
    UpdateProprietarySingleElementFromDokumentUseCase {

  final LoadDokumentPort loadDokumentPort;
  private final LoadNormPort loadNormPort;
  private final NormService normService;

  ProprietaryService(
    LoadDokumentPort loadDokumentPort,
    LoadNormPort loadNormPort,
    NormService normService
  ) {
    this.loadDokumentPort = loadDokumentPort;
    this.loadNormPort = loadNormPort;
    this.normService = normService;
  }

  @Override
  public Proprietary loadProprietaryFromDokument(
    LoadProprietaryFromDokumentUseCase.Options options
  ) {
    return loadDokumentPort
      .loadDokument(new LoadDokumentPort.Options(options.dokumentExpressionEli()))
      .map(m -> m.getMeta().getOrCreateProprietary())
      .orElseThrow(() -> new DokumentNotFoundException(options.dokumentExpressionEli().toString()));
  }

  @Override
  public RahmenMetadata updateProprietaryFrameFromDokument(
    UpdateProprietaryFrameFromDokumentUseCase.Options options
  ) {
    final Norm norm = loadNormPort
      .loadNorm(new LoadNormPort.Options(options.dokumentExpressionEli().asNormEli()))
      .orElseThrow(() -> new NormNotFoundException(options.dokumentExpressionEli().asNormEli()));

    final RahmenMetadata rahmenMetadata = norm.getRahmenMetadata();
    rahmenMetadata.setFna(options.inputMetadata().fna());
    rahmenMetadata.setTyp(options.inputMetadata().typ());
    rahmenMetadata.setSubtyp(options.inputMetadata().subtyp());
    rahmenMetadata.setBezeichnungInVorlage(options.inputMetadata().bezeichnungInVorlage());
    rahmenMetadata.setArtDerNorm(options.inputMetadata().artDerNorm());
    rahmenMetadata.setStaat(options.inputMetadata().staat());
    rahmenMetadata.setBeschliessendesOrgan(options.inputMetadata().beschliessendesOrgan());
    rahmenMetadata.setQualifizierterMehrheit(options.inputMetadata().qualifizierterMehrheit());
    rahmenMetadata.setRessort(options.inputMetadata().ressort());
    rahmenMetadata.setOrganisationsEinheit(options.inputMetadata().organisationsEinheit());

    return normService.updateNorm(norm).getRahmenMetadata();
  }

  @Override
  public Proprietary updateProprietarySingleElementFromDokument(
    UpdateProprietarySingleElementFromDokumentUseCase.Options options
  ) {
    final Norm norm = loadNormPort
      .loadNorm(new LoadNormPort.Options(options.dokumentExpressionEli().asNormEli()))
      .orElseThrow(() -> new NormNotFoundException(options.dokumentExpressionEli().asNormEli()));

    Dokument dokument = norm
      .getDokumentByEli(options.dokumentExpressionEli())
      .orElseThrow(() -> new DokumentNotFoundException(options.dokumentExpressionEli().toString()));

    final Proprietary proprietary = dokument.getMeta().getOrCreateProprietary();
    proprietary.setMetadataValue(
      Metadata.ART_DER_NORM,
      options.eid(),
      options.inputMetadata().artDerNorm()
    );

    final Norm updatedNorm = normService.updateNorm(norm);

    return updatedNorm
      .getDokumentByEli(options.dokumentExpressionEli())
      .orElseThrow()
      .getMeta()
      .getOrCreateProprietary();
  }
}
