package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.domain.entity.*;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentManifestationEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormExpressionEli;
import de.bund.digitalservice.ris.norms.domain.entity.metadata.rahmen.RahmenMetadata;
import de.bund.digitalservice.ris.norms.utils.NodeCreator;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Service for creating new expressions and manifestations of a norm.
 */
@Service
public class CreateNewWorkService {

  /**
   * Creates a completely new work out of an embedded norm, the Rechtsetzungsdokument of the Verkündung and the generated expression eli for the new norm computed by the zielnorm-reference
   * The Rechtsetzungsdokument of the new work is created out of the newly created Regelungstext. The redok metadata are copied from the Rechtsetzungsdokument of the Verkündung.
   * @param embeddedNorm taken out of the Verkündung (not loaded from the DB)
   * @param rechtsetzungsdokumentVerkuendung needed for the redok metadata of the new Rechtsetzungsdokument of the new work
   * @param expressionEli the expression eli to be used for the new work
   * @return the new work
   */
  public Norm createNewWork(
    Norm embeddedNorm,
    Rechtsetzungsdokument rechtsetzungsdokumentVerkuendung,
    NormExpressionEli expressionEli
  ) {
    var newNorm = new Norm(embeddedNorm);

    // Counter needed
    AtomicInteger counterOffeneStruktur = new AtomicInteger(1);
    newNorm
      .getDokumente()
      // Sorting for the subtype, so that when replacing anlage-regelungstext-X with the counter they are already sorted
      .stream()
      .sorted(Comparator.comparing(Dokument::getManifestationEli))
      .forEach(dokument -> {
        final DokumentManifestationEli dokumentManifestationEli = dokument.getManifestationEli();
        final String newNaturalIdentifier = expressionEli.getNaturalIdentifier();
        final LocalDate newPointInTime = expressionEli.getPointInTime();
        dokumentManifestationEli.setNaturalIdentifier(newNaturalIdentifier);
        dokumentManifestationEli.setPointInTime(newPointInTime);
        dokumentManifestationEli.setPointInTimeManifestation(Norm.WORKING_COPY_DATE);

        String newSubtype = "";
        if (dokument instanceof Regelungstext) {
          // For regelungstext is just simple, we only have 1
          newSubtype = dokumentManifestationEli.getSubtype().replaceAll("-(\\d+)$", "-1");
        } else if (dokument instanceof OffeneStruktur) {
          // For offeneStruktur we may have >1 and we just need to number them using the counter
          newSubtype = dokumentManifestationEli
            .getSubtype()
            .replaceAll("-(\\d+)$", "-" + counterOffeneStruktur);
          counterOffeneStruktur.getAndIncrement();
        }
        dokumentManifestationEli.setSubtype(newSubtype);

        dokument.getMeta().getFRBRManifestation().setEli(dokumentManifestationEli);
        dokument.getMeta().getFRBRManifestation().setURI(dokumentManifestationEli.toUri());
        dokument.getMeta().getFRBRExpression().setEli(dokumentManifestationEli.asExpressionEli());
        dokument
          .getMeta()
          .getFRBRExpression()
          .setURI(dokumentManifestationEli.asExpressionEli().toUri());
        dokument.getMeta().getFRBRExpression().setFBRDate(newPointInTime.toString(), "verkuendung");
        dokument.getMeta().getFRBRWork().setEli(dokumentManifestationEli.asWorkEli());
        dokument.getMeta().getFRBRWork().setURI(dokumentManifestationEli.asWorkEli().toUri());
        dokument.getMeta().getFRBRWork().setFRBRnumber(newNaturalIdentifier);
        dokument.getMeta().getFRBRWork().setFRBRsubtype(newSubtype);
        // New GUID for übergreifende-id and aktuelle-version-id
        dokument.setGuid(UUID.randomUUID());
        dokument.setUebergreifendeGuid(UUID.randomUUID());
        // Remove just in case previous and next GUIDs
        dokument.getMeta().getFRBRExpression().deleteAliasPreviousVersionId();
        dokument.getMeta().getFRBRExpression().deleteAliasNextVersionId();
      });
    // set regtxt:form to stammform
    final RahmenMetadata rahmenMetadata = newNorm.getRahmenMetadata();
    rahmenMetadata.setForm("stammform");

    var rechtsetzungsdokument = createRechtsetzungsdokumentFromRegelungstext(
      new Regelungstext(newNorm.getRegelungstext1()),
      rechtsetzungsdokumentVerkuendung
    );
    newNorm.getDokumente().add(rechtsetzungsdokument);

    return newNorm;
  }

  private Rechtsetzungsdokument createRechtsetzungsdokumentFromRegelungstext(
    final Regelungstext regelungstext,
    final Rechtsetzungsdokument rechtsetzungsdokument
  ) {
    // Replace <akn:act> element with <akn:documentCollection>
    DokumentManifestationEli dokumentManifestationEli = regelungstext.getManifestationEli();
    Meta oldMeta = regelungstext.getMeta();
    Document doc = regelungstext.getDocument();
    var actElement = NodeParser.getMandatoryElementFromExpression(".//act", doc);
    actElement.getParentNode().removeChild(actElement);
    Element documentCollection = NodeCreator.createElement(
      Namespace.INHALTSDATEN,
      "documentCollection",
      doc.getDocumentElement()
    );
    documentCollection.setAttribute(
      "name",
      "/akn/ontology/de/concept/documenttype/bund/rechtsetzungsdokument"
    );

    // Copy meta but only identification and proprietary
    Element newMetaElement = NodeCreator.createElementWithEidAndGuid(
      Namespace.INHALTSDATEN,
      "meta",
      documentCollection
    );
    NodeParser.nodeListToList(oldMeta.getElement().getChildNodes()).forEach(child -> {
        if (
          child.getNodeType() == Node.ELEMENT_NODE &&
          List.of("akn:identification", "akn:proprietary").contains(child.getNodeName())
        ) {
          newMetaElement.appendChild(child);
        }
      });
    // Patch FRBR @values
    final Meta newMeta = new Meta(newMetaElement);
    newMeta.getFRBRWork().setFRBRsubtype("rechtsetzungsdokument-1");
    var eli = newMeta.getFRBRManifestation().getEli();
    eli.setSubtype("rechtsetzungsdokument-1");
    newMeta.getFRBRWork().setEli(eli.asWorkEli());
    newMeta.getFRBRExpression().setEli(eli.asExpressionEli());
    newMeta.getFRBRManifestation().setEli(eli);
    newMeta.getFRBRManifestation().setURI(eli.toUri());

    var proprietary = newMeta.getOrCreateProprietary();
    proprietary
      .getMetadataParent(Namespace.METADATEN_REGELUNGSTEXT)
      .ifPresent(regtxtMetadata -> proprietary.getElement().removeChild(regtxtMetadata));
    rechtsetzungsdokument
      .getMeta()
      .getOrCreateProprietary()
      .getMetadataParent(Namespace.METADATEN_RECHTSETZUNGSDOKUMENT)
      .ifPresent(redokMetadata -> {
        // To copy 1 node from 1 doc to another doc we need to import the node first using the owner document
        Node imported = proprietary.getElement().getOwnerDocument().importNode(redokMetadata, true);
        proprietary.getElement().appendChild(imported);
      });

    // Add <collectionBody> → <component> → <documentRef>
    Element collectionBody = NodeCreator.createElementWithEidAndGuid(
      Namespace.INHALTSDATEN,
      "collectionBody",
      documentCollection
    );
    Element component = NodeCreator.createElementWithEidAndGuid(
      Namespace.INHALTSDATEN,
      "component",
      collectionBody
    );
    Element documentRef = NodeCreator.createElementWithEidAndGuid(
      Namespace.INHALTSDATEN,
      "documentRef",
      component
    );
    documentRef.setAttribute("href", dokumentManifestationEli.asNormEli() + "/regelungstext-1.xml");
    documentRef.setAttribute(
      "showAs",
      "/akn/ontology/de/concept/documenttype/bund/regelungstext-verkuendung"
    );

    return new Rechtsetzungsdokument(doc);
  }
}
