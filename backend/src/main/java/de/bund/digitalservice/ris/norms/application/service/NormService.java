package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.exception.InvalidUpdateException;
import de.bund.digitalservice.ris.norms.application.exception.NormNotFoundException;
import de.bund.digitalservice.ris.norms.application.exception.RegelungstextNotFoundException;
import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormPort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadRegelungstextPort;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.*;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import de.bund.digitalservice.ris.norms.utils.EidConsistencyGuardian;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.util.*;
import org.springframework.stereotype.Service;

/**
 * Service class within the application core part of the backend. It is responsible for implementing
 * all the input ports. It is annotated with {@link Service} to indicate that it's a service
 * component in the Spring context.
 */
@Service
public class NormService
  implements
    LoadNormUseCase,
    LoadRegelungstextXmlUseCase,
    UpdateRegelungstextXmlUseCase,
    LoadRegelungstextUseCase,
    LoadZielnormReferencesUseCase,
    UpdateZielnormReferencesUseCase,
    DeleteZielnormReferencesUseCase {

  private final LoadNormPort loadNormPort;
  private final UpdateNormPort updateNormPort;
  private final LoadRegelungstextPort loadRegelungstextPort;

  public NormService(
    LoadNormPort loadNormPort,
    UpdateNormPort updateNormPort,
    LoadRegelungstextPort loadRegelungstextPort
  ) {
    this.loadNormPort = loadNormPort;
    this.updateNormPort = updateNormPort;
    this.loadRegelungstextPort = loadRegelungstextPort;
  }

  @Override
  public Norm loadNorm(final LoadNormUseCase.Query query) {
    return loadNormPort
      .loadNorm(new LoadNormPort.Command(query.eli()))
      .orElseThrow(() -> new NormNotFoundException(query.eli().toString()));
  }

  @Override
  public Regelungstext loadRegelungstext(final LoadRegelungstextUseCase.Query query) {
    return loadRegelungstextPort
      .loadRegelungstext(new LoadRegelungstextPort.Command(query.eli()))
      .orElseThrow(() -> new RegelungstextNotFoundException(query.eli().toString()));
  }

  @Override
  public String loadRegelungstextXml(final LoadRegelungstextXmlUseCase.Query query) {
    final Regelungstext regelungstext = loadRegelungstextPort
      .loadRegelungstext(new LoadRegelungstextPort.Command(query.eli()))
      .orElseThrow(() -> new RegelungstextNotFoundException(query.eli().toString()));

    return XmlMapper.toString(regelungstext.getDocument());
  }

  @Override
  public String updateRegelungstextXml(UpdateRegelungstextXmlUseCase.Query query) {
    var regelungstextToBeUpdated = new Regelungstext(XmlMapper.toDocument(query.xml()));

    var existingNorm = loadNormPort
      .loadNorm(new LoadNormPort.Command(query.eli().asNormEli()))
      .orElseThrow(() -> new NormNotFoundException(query.eli().asNormEli().toString()));
    var existingRegelungstext = existingNorm
      .getRegelungstextByEli(query.eli())
      .orElseThrow(() -> new RegelungstextNotFoundException(query.eli().toString()));

    if (
      !existingRegelungstext.getExpressionEli().equals(regelungstextToBeUpdated.getExpressionEli())
    ) {
      throw new InvalidUpdateException("Changing the ELI is not supported.");
    }

    if (!existingRegelungstext.getGuid().equals(regelungstextToBeUpdated.getGuid())) {
      throw new InvalidUpdateException("Changing the GUID is not supported.");
    }

    var regelungstexte = new HashSet<>(existingNorm.getRegelungstexte());
    regelungstexte.remove(existingRegelungstext);
    regelungstexte.add(regelungstextToBeUpdated);
    existingNorm.setRegelungstexte(regelungstexte);

    var updatedNorm = updateNorm(existingNorm).get(query.eli().asNormEli());
    var updatedRegelungstext = updatedNorm.getRegelungstextByEli(query.eli()).orElseThrow();

    return XmlMapper.toString(updatedRegelungstext.getDocument());
  }

  /**
   * It not only saves a {@link Norm} but makes sure that all Eids are consistent.
   *
   * @param normToBeUpdated the norm which shall be saved
   * @return An {@link Map} containing the updated and saved {@link Norm}
   * @throws NormNotFoundException if the norm cannot be found
   */
  public Map<NormExpressionEli, Norm> updateNorm(Norm normToBeUpdated) {
    normToBeUpdated
      .getDokumente()
      .forEach(dokument -> {
        EidConsistencyGuardian.eliminateDeadReferences(dokument.getDocument());
        EidConsistencyGuardian.correctEids(dokument.getDocument());
      });

    Norm savedNorm = updateNormPort
      .updateNorm(new UpdateNormPort.Command(normToBeUpdated))
      .orElseThrow(() -> new NormNotFoundException(normToBeUpdated.getManifestationEli().toString())
      );

    return Map.of(normToBeUpdated.getExpressionEli(), savedNorm);
  }

  @Override
  public List<ZielnormReference> loadZielnormReferences(LoadZielnormReferencesUseCase.Query query) {
    return loadNorm(new LoadNormUseCase.Query(query.eli()))
      .getRegelungstext1()
      .getMeta()
      .getProprietary()
      .flatMap(Proprietary::getCustomModsMetadata)
      .flatMap(CustomModsMetadata::getZielnormenReferences)
      .stream()
      .flatMap(ZielnormReferences::stream)
      .toList();
  }

  @Override
  public List<ZielnormReference> updateZielnormReferences(
    UpdateZielnormReferencesUseCase.Query query
  ) {
    var norm = loadNorm(new LoadNormUseCase.Query(query.eli()));
    var zielnormReferences = norm
      .getRegelungstext1()
      .getMeta()
      .getOrCreateProprietary()
      .getOrCreateCustomModsMetadata()
      .getOrCreateZielnormenReferences();

    query
      .zielnormReferences()
      .forEach(zielnormReferenceUpdateData -> {
        var zielnormReference = zielnormReferences
          .stream()
          .filter(reference -> reference.getEId().equals(zielnormReferenceUpdateData.eId()))
          .findFirst()
          .orElseGet(() ->
            zielnormReferences.add(
              zielnormReferenceUpdateData.typ(),
              zielnormReferenceUpdateData.geltungszeit(),
              zielnormReferenceUpdateData.eId(),
              zielnormReferenceUpdateData.zielnorm()
            )
          );

        zielnormReference.setTyp(zielnormReferenceUpdateData.typ());
        zielnormReference.setGeltungszeit(zielnormReferenceUpdateData.geltungszeit());
        zielnormReference.setZielnorm(zielnormReferenceUpdateData.zielnorm());
      });

    updateNorm(norm);

    return zielnormReferences.stream().toList();
  }

  @Override
  public List<ZielnormReference> deleteZielnormReferences(
    DeleteZielnormReferencesUseCase.Query query
  ) {
    var norm = loadNorm(new LoadNormUseCase.Query(query.eli()));
    var zielnormReferences = norm
      .getRegelungstext1()
      .getMeta()
      .getOrCreateProprietary()
      .getOrCreateCustomModsMetadata()
      .getOrCreateZielnormenReferences();

    query
      .zielnormReferenceEIds()
      .forEach(eId ->
        zielnormReferences
          .stream()
          .filter(reference -> reference.getEId().equals(eId))
          .forEach(zielnormReferences::remove)
      );

    updateNorm(norm);

    return zielnormReferences.stream().toList();
  }
}
