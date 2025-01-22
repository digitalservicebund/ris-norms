package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.exception.NormNotFoundException;
import de.bund.digitalservice.ris.norms.application.port.input.LoadProprietaryFromNormUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.UpdateProprietaryFrameFromNormUseCase;
import de.bund.digitalservice.ris.norms.application.port.input.UpdateProprietarySingleElementFromNormUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormPort;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.*;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import java.util.Map;
import org.springframework.stereotype.Service;

/** Implements operations related to the "proprietary" of a {@link Norm} */
@Service
public class ProprietaryService
  implements
    LoadProprietaryFromNormUseCase,
    UpdateProprietaryFrameFromNormUseCase,
    UpdateProprietarySingleElementFromNormUseCase {

  final LoadNormPort loadNormPort;
  final UpdateNormPort updateNormPort;
  private final NormService normService;

  ProprietaryService(
    LoadNormPort loadNormPort,
    UpdateNormPort updateNormPort,
    NormService normService
  ) {
    this.loadNormPort = loadNormPort;
    this.updateNormPort = updateNormPort;
    this.normService = normService;
  }

  @Override
  public Proprietary loadProprietaryFromNorm(LoadProprietaryFromNormUseCase.Query query) {
    return loadNormPort
      .loadNorm(new LoadNormPort.Command(query.eli()))
      .map(m -> m.getMeta().getOrCreateProprietary())
      .orElseThrow(() -> new NormNotFoundException(query.eli().toString()));
  }

  @Override
  public Proprietary updateProprietaryFrameFromNorm(
    UpdateProprietaryFrameFromNormUseCase.Query query
  ) {
    final Norm norm = loadNormPort
      .loadNorm(new LoadNormPort.Command(query.eli()))
      .orElseThrow(() -> new NormNotFoundException(query.eli().toString()));
    final Proprietary proprietary = norm.getMeta().getOrCreateProprietary();
    final MetadatenDs metadatenDs = proprietary.getOrCreateMetadatenDs();
    final MetadatenBund metadatenBund = proprietary.getOrCreateMetadatenBund();

    metadatenDs.updateSimpleMetadatum(
      MetadatenDs.Metadata.FNA,
      query.atDate(),
      query.metadata().fna()
    );
    metadatenDs.updateSimpleMetadatum(
      MetadatenDs.Metadata.ART,
      query.atDate(),
      query.metadata().art()
    );
    metadatenDs.updateSimpleMetadatum(
      MetadatenDs.Metadata.TYP,
      query.atDate(),
      query.metadata().typ()
    );
    metadatenDs.updateSimpleMetadatum(
      MetadatenDs.Metadata.SUBTYP,
      query.atDate(),
      query.metadata().subtyp()
    );
    metadatenDs.updateSimpleMetadatum(
      MetadatenDs.Metadata.BEZEICHNUNG_IN_VORLAGE,
      query.atDate(),
      query.metadata().bezeichnungInVorlage()
    );
    metadatenDs.updateSimpleMetadatum(
      MetadatenDs.Metadata.ART_DER_NORM,
      query.atDate(),
      query.metadata().artDerNorm()
    );
    metadatenDs.updateSimpleMetadatum(
      MetadatenDs.Metadata.STAAT,
      query.atDate(),
      query.metadata().staat()
    );
    metadatenDs.updateSimpleMetadatum(
      MetadatenDs.Metadata.BESCHLIESSENDES_ORGAN,
      query.atDate(),
      query.metadata().beschliessendesOrgan()
    );
    metadatenDs.setAttributeOfSimpleMetadatum(
      MetadatenDs.Attribute.QUALIFIZIERTE_MEHRHEIT,
      query.atDate(),
      String.valueOf(query.metadata().qualifizierterMehrheit()).equals("null")
        ? "false"
        : String.valueOf(query.metadata().qualifizierterMehrheit())
    );
    metadatenBund.updateSimpleMetadatum(
      MetadatenBund.Metadata.RESSORT,
      query.atDate(),
      query.metadata().ressort()
    );
    metadatenDs.updateSimpleMetadatum(
      MetadatenDs.Metadata.ORGANISATIONS_EINHEIT,
      query.atDate(),
      query.metadata().organisationsEinheit()
    );

    Map<NormExpressionEli, Norm> updatedNorms = normService.updateNorm(norm);

    Norm updatedNorm = updatedNorms.get(query.eli().asNormEli());
    if (updatedNorm != null) return updatedNorm
      .getMeta()
      .getOrCreateProprietary(); else return null;
  }

  @Override
  public Proprietary updateProprietarySingleElementFromNorm(
    UpdateProprietarySingleElementFromNormUseCase.Query query
  ) {
    final Norm norm = loadNormPort
      .loadNorm(new LoadNormPort.Command(query.eli()))
      .orElseThrow(() -> new NormNotFoundException(query.eli().toString()));
    final Proprietary proprietary = norm.getMeta().getOrCreateProprietary();
    final MetadatenDs metadatenDs = proprietary.getOrCreateMetadatenDs();

    metadatenDs.updateSingleElementSimpleMetadatum(
      Einzelelement.Metadata.ART_DER_NORM,
      query.eid(),
      query.atDate(),
      query.metadata().artDerNorm()
    );

    normService.updateNorm(norm);

    return proprietary;
  }
}
