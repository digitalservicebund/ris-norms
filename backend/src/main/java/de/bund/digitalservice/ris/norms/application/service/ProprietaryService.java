package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.port.input.LoadProprietaryFromNormUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.UpdateProprietaryFromNormUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormPort;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.MetadatenDs;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.Proprietary;
import de.bund.digitalservice.ris.norms.utils.exceptions.NormNotFoundException;
import org.springframework.stereotype.Service;

/** Implements operations related to the "proprietary" of a {@link Norm} */
@Service
public class ProprietaryService
    implements LoadProprietaryFromNormUseCase, UpdateProprietaryFromNormUseCase {

  final LoadNormPort loadNormPort;
  final UpdateNormPort updateNormPort;

  ProprietaryService(LoadNormPort loadNormPort, UpdateNormPort updateNormPort) {
    this.loadNormPort = loadNormPort;
    this.updateNormPort = updateNormPort;
  }

  @Override
  public Proprietary loadProprietaryFromNorm(LoadProprietaryFromNormUseCase.Query query)
      throws NormNotFoundException {
    return loadNormPort
        .loadNorm(new LoadNormPort.Command(query.eli()))
        .map(m -> m.getMeta().getOrCreateProprietary())
        .orElseThrow(() -> new NormNotFoundException((query.eli())));
  }

  @Override
  public Proprietary updateProprietaryFromNorm(UpdateProprietaryFromNormUseCase.Query query)
      throws NormNotFoundException {
    final Norm norm =
        loadNormPort
            .loadNorm(new LoadNormPort.Command(query.eli()))
            .orElseThrow(() -> new NormNotFoundException((query.eli())));
    final Proprietary proprietary = norm.getMeta().getOrCreateProprietary();
    final MetadatenDs metadatenDs = proprietary.getOrCreateMetadatenDs();

    metadatenDs.setSimpleMetadatum(
        MetadatenDs.SimpleMetadatum.FNA, query.atDate(), query.metadata().fna());
    metadatenDs.setSimpleMetadatum(
        MetadatenDs.SimpleMetadatum.ART, query.atDate(), query.metadata().art());
    metadatenDs.setSimpleMetadatum(
        MetadatenDs.SimpleMetadatum.TYP, query.atDate(), query.metadata().typ());
    metadatenDs.setSimpleMetadatum(
        MetadatenDs.SimpleMetadatum.SUBTYP, query.atDate(), query.metadata().subtyp());
    metadatenDs.setSimpleMetadatum(
        MetadatenDs.SimpleMetadatum.BEZEICHNUNG_IN_VORLAGE,
        query.atDate(),
        query.metadata().bezeichnungInVorlage());
    metadatenDs.setSimpleMetadatum(
        MetadatenDs.SimpleMetadatum.ART_DER_NORM, query.atDate(), query.metadata().artDerNorm());
    metadatenDs.setSimpleMetadatum(
        MetadatenDs.SimpleMetadatum.NORMGEBER, query.atDate(), query.metadata().normgeber());
    metadatenDs.setSimpleMetadatum(
        MetadatenDs.SimpleMetadatum.BESCHLIESSENDES_ORGAN,
        query.atDate(),
        query.metadata().beschliessendesOrgan());
    metadatenDs.setAttributeOfSimpleMetadatum(
        MetadatenDs.Attribute.QUALIFIZIERTE_MEHRHEIT,
        query.atDate(),
        String.valueOf(query.metadata().qualifizierterMehrheit()).equals("null")
            ? "false"
            : String.valueOf(query.metadata().qualifizierterMehrheit()));

    updateNormPort.updateNorm(new UpdateNormPort.Command(norm));

    return proprietary;
  }
}
