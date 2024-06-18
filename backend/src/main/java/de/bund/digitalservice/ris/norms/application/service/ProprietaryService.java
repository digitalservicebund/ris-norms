package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.port.input.LoadProprietaryFromNormUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.UpdateProprietaryFromNormUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormPort;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.MetadatenDs;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.Proprietary;
import de.bund.digitalservice.ris.norms.utils.exceptions.NormNotFoundException;
import java.util.HashMap;
import java.util.Map;
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

    metadatenDs.setSimpleProprietaryMetadata(
        MetadatenDs.SimpleMetadatum.FNA, query.atDate(), query.metadata().fna());
    metadatenDs.setSimpleProprietaryMetadata(
        MetadatenDs.SimpleMetadatum.ART, query.atDate(), query.metadata().art());
    metadatenDs.setSimpleProprietaryMetadata(
        MetadatenDs.SimpleMetadatum.TYP, query.atDate(), query.metadata().typ());
    metadatenDs.setSimpleProprietaryMetadata(
        MetadatenDs.SimpleMetadatum.SUBTYP, query.atDate(), query.metadata().subtyp());
    metadatenDs.setSimpleProprietaryMetadata(
        MetadatenDs.SimpleMetadatum.BEZEICHNUNG_IN_VORLAGE,
        query.atDate(),
        query.metadata().bezeichnungInVorlage());
    metadatenDs.setSimpleProprietaryMetadata(
        MetadatenDs.SimpleMetadatum.ART_DER_NORM, query.atDate(), query.metadata().artDerNorm());
    metadatenDs.setSimpleProprietaryMetadata(
        MetadatenDs.SimpleMetadatum.NORMGEBER, query.atDate(), query.metadata().normgeber());

    // TODO not so nice: 1) string literal, 2) next 5 lines in general
    Map<String, Object> map = new HashMap<>();
    if (query.metadata().beschliessendesOrgan().qualifizierteMehrheit() != null) {
      map.put(
          "qualifizierteMehrheit", query.metadata().beschliessendesOrgan().qualifizierteMehrheit());
    }

    metadatenDs.setComplexProprietaryMetadata(
        MetadatenDs.ComplexMetadatum.BESCHLIESSENDES_ORGAN,
        query.atDate(),
        query.metadata().beschliessendesOrgan().value(),
        map);

    updateNormPort.updateNorm(new UpdateNormPort.Command(norm));

    return proprietary;
  }
}
