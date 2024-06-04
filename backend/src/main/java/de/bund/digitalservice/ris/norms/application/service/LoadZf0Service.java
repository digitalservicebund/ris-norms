package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.port.input.LoadZf0UseCase;
import de.bund.digitalservice.ris.norms.application.port.input.UpdatePassiveModificationsUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormByGuidPort;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateOrSaveNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.Eli;
import de.bund.digitalservice.ris.norms.domain.entity.FRBRExpression;
import de.bund.digitalservice.ris.norms.domain.entity.FRBRManifestation;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.utils.exceptions.XmlProcessingException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * Service for loading ZF0 versions of norms. If not present in DB, it will be created using the
 * amending law and target law.
 */
@Service
public class LoadZf0Service implements LoadZf0UseCase {

  private final UpdateNormService updateNormService;
  private final LoadNormByGuidPort loadNormByGuidPort;
  private final UpdateOrSaveNormPort updateOrSaveNormPort;

  public LoadZf0Service(
      final UpdateNormService updateNormService,
      final LoadNormByGuidPort loadNormByGuidPort,
      final UpdateOrSaveNormPort updateOrSaveNormPort) {
    this.updateNormService = updateNormService;
    this.loadNormByGuidPort = loadNormByGuidPort;
    this.updateOrSaveNormPort = updateOrSaveNormPort;
  }

  @Override
  public Norm loadZf0(Query query) {

    final Norm amendingNorm = query.amendingLaw();
    final Norm targetNorm = query.targetLaw();

    final Optional<Norm> optionalZf0LawDB =
        targetNorm
            .getNextVersionGuid()
            .flatMap(
                guid -> loadNormByGuidPort.loadNormByGuid(new LoadNormByGuidPort.Command(guid)));

    if (optionalZf0LawDB.isPresent()) {
      return optionalZf0LawDB.get();
    }

    final Norm zf0Norm = Norm.builder().document(cloneDocument(targetNorm.getDocument())).build();

    final String announcementDateAmendingLaw =
        amendingNorm.getFBRDateVerkuendung().orElseThrow().format(DateTimeFormatter.ISO_LOCAL_DATE);

    updateFRBRExpression(zf0Norm, targetNorm, announcementDateAmendingLaw);
    updateFRBRManifestation(zf0Norm, announcementDateAmendingLaw);
    updateNormService.updatePassiveModifications(
        new UpdatePassiveModificationsUseCase.Query(zf0Norm, amendingNorm, targetNorm.getEli()));

    if (query.persistZf0()) {
      updateOrSaveNormPort.updateOrSave(new UpdateOrSaveNormPort.Command(zf0Norm));
    }
    return zf0Norm;
  }

  private void updateFRBRExpression(
      final Norm zf0Norm, final Norm targetNorm, final String announcementDateAmendingLaw) {
    final FRBRExpression frbrExpression = zf0Norm.getMeta().getFRBRExpression();

    // 1.FRBRalias (vorherige-version-id / aktuelle-version-id / nachfolgende-version-id)
    final UUID previousVersionId =
        targetNorm.getMeta().getFRBRExpression().getFRBRaliasCurrentVersionId();
    frbrExpression.setFRBRaliasPreviousVersionId(previousVersionId);
    final UUID currentVersionId =
        targetNorm.getMeta().getFRBRExpression().getFRBRaliasNextVersionId();
    frbrExpression.setFRBRaliasCurrentVersionId(currentVersionId);
    frbrExpression.setFRBRaliasNextVersionId(UUID.randomUUID());

    // 2. new eli of zfo
    final Eli zf0Eli = new Eli(frbrExpression.getEli());
    zf0Eli.setPointInTime(announcementDateAmendingLaw);
    frbrExpression.setEli(zf0Eli.getValue());

    // 3. FRBRDate --> announcement date of amending law + @name="aenderung"
    frbrExpression.setFBRDate(announcementDateAmendingLaw, "aenderung");
  }

  private void updateFRBRManifestation(
      final Norm zf0Norm, final String announcementDateAmendingLaw) {
    final FRBRManifestation frbrManifestation = zf0Norm.getMeta().getFRBRManifestation();

    // 1.replace date of eli parts
    final Eli zf0Eli = new Eli(frbrManifestation.getEli());
    zf0Eli.setPointInTime(announcementDateAmendingLaw);
    frbrManifestation.setEli(zf0Eli.getValue());

    // 2. FRBRdate --> current system date + @name="generierung"
    frbrManifestation.setFBRDate(LocalDate.now().toString(), "generierung");
  }

  private Document cloneDocument(Document originalDocument) {
    try {
      final Node originalRootNode = originalDocument.getDocumentElement();
      final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      final DocumentBuilder db = dbf.newDocumentBuilder();

      final Document clonedDocument = db.newDocument();
      final Node clonedRootNode = clonedDocument.importNode(originalRootNode, true);
      clonedDocument.appendChild(clonedRootNode);
      return clonedDocument;
    } catch (ParserConfigurationException e) {
      throw new XmlProcessingException(e.getMessage(), e);
    }
  }
}
