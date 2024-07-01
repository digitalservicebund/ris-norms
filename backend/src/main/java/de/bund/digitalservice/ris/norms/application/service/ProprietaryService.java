package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.exception.NormNotFoundException;
import de.bund.digitalservice.ris.norms.application.port.input.LoadProprietaryFromNormUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.UpdateProprietaryFromNormUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.UpdateProprietarySingleElementFromNormUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormPort;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.*;
import org.springframework.stereotype.Service;

/** Implements operations related to the "proprietary" of a {@link Norm} */
@Service
public class ProprietaryService
    implements LoadProprietaryFromNormUseCase,
        UpdateProprietaryFromNormUseCase,
        UpdateProprietarySingleElementFromNormUseCase {

  final LoadNormPort loadNormPort;
  final UpdateNormPort updateNormPort;

  ProprietaryService(LoadNormPort loadNormPort, UpdateNormPort updateNormPort) {
    this.loadNormPort = loadNormPort;
    this.updateNormPort = updateNormPort;
  }

  @Override
  public Proprietary loadProprietaryFromNorm(LoadProprietaryFromNormUseCase.Query query) {
    return loadNormPort
        .loadNorm(new LoadNormPort.Command(query.eli()))
        .map(m -> m.getMeta().getOrCreateProprietary())
        .orElseThrow(() -> new NormNotFoundException((query.eli())));
  }

  @Override
  public Proprietary updateProprietaryFrameFromNorm(UpdateProprietaryFromNormUseCase.Query query)
      throws NormNotFoundException {
    final Norm norm =
        loadNormPort
            .loadNorm(new LoadNormPort.Command(query.eli()))
            .orElseThrow(() -> new NormNotFoundException((query.eli())));
    final Proprietary proprietary = norm.getMeta().getOrCreateProprietary();
    final MetadatenDs metadatenDs = proprietary.getOrCreateMetadatenDs();
    final MetadatenDe metadatenDe = proprietary.getOrCreateMetadatenDe();

    metadatenDs.updateFrameSimpleMetadatum(
        MetadatenDs.Metadata.FNA, query.atDate(), query.metadata().fna());
    metadatenDs.updateFrameSimpleMetadatum(
        MetadatenDs.Metadata.ART, query.atDate(), query.metadata().art());
    metadatenDs.updateFrameSimpleMetadatum(
        MetadatenDs.Metadata.TYP, query.atDate(), query.metadata().typ());
    metadatenDs.updateFrameSimpleMetadatum(
        MetadatenDs.Metadata.SUBTYP, query.atDate(), query.metadata().subtyp());
    metadatenDs.updateFrameSimpleMetadatum(
        MetadatenDs.Metadata.BEZEICHNUNG_IN_VORLAGE,
        query.atDate(),
        query.metadata().bezeichnungInVorlage());
    metadatenDs.updateFrameSimpleMetadatum(
        MetadatenDs.Metadata.ART_DER_NORM, query.atDate(), query.metadata().artDerNorm());
    metadatenDs.updateFrameSimpleMetadatum(
        MetadatenDs.Metadata.NORMGEBER, query.atDate(), query.metadata().normgeber());
    metadatenDs.updateFrameSimpleMetadatum(
        MetadatenDs.Metadata.BESCHLIESSENDES_ORGAN,
        query.atDate(),
        query.metadata().beschliessendesOrgan());
    metadatenDs.setAttributeOfSimpleMetadatum(
        MetadatenDs.Attribute.QUALIFIZIERTE_MEHRHEIT,
        query.atDate(),
        String.valueOf(query.metadata().qualifizierterMehrheit()).equals("null")
            ? "false"
            : String.valueOf(query.metadata().qualifizierterMehrheit()));
    metadatenDe.updateFrameSimpleMetadatum(
        MetadatenDe.Metadata.FEDERFUEHRUNG, query.atDate(), query.metadata().federfuehrung());
    metadatenDs.updateFrameSimpleMetadatum(
        MetadatenDs.Metadata.ORGANISATIONS_EINHEIT,
        query.atDate(),
        query.metadata().organisationsEinheit());

    updateNormPort.updateNorm(new UpdateNormPort.Command(norm));

    return proprietary;
  }

  @Override
  public Proprietary updateProprietarySingleElementFromNorm(
      UpdateProprietarySingleElementFromNormUseCase.Query query) throws NormNotFoundException {
    final Norm norm =
        loadNormPort
            .loadNorm(new LoadNormPort.Command(query.eli()))
            .orElseThrow(() -> new NormNotFoundException((query.eli())));
    final Proprietary proprietary = norm.getMeta().getOrCreateProprietary();
    final MetadatenDs metadatenDs = proprietary.getOrCreateMetadatenDs();

    metadatenDs.updateSingleElementSimpleMetadatum(
        Einzelelement.Metadata.ART_DER_NORM,
        query.eid(),
        query.atDate(),
        query.metadata().artDerNorm());

    updateNormPort.updateNorm(new UpdateNormPort.Command(norm));

    return proprietary;
  }
}
