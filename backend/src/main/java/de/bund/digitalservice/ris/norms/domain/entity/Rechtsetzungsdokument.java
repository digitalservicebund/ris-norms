package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.domain.entity.metadata.rahmen.RechtsetzungsdokumentRahmenMetadata;
import lombok.Getter;
import org.w3c.dom.Document;

/**
 * Represents the "Rechtsetzungsdokument" of a norm in LDML.de.
 */
@Getter
public non-sealed class Rechtsetzungsdokument extends Dokument {

  @Override
  public Dokument copy() {
    return new Rechtsetzungsdokument(this);
  }

  public Rechtsetzungsdokument(Rechtsetzungsdokument rechtsetzungsdokument) {
    this((Document) rechtsetzungsdokument.getDocument().cloneNode(true));
  }

  public Rechtsetzungsdokument(Document document) {
    super(document);
  }

  @Override
  public RechtsetzungsdokumentRahmenMetadata getRahmenMetadata() {
    return new RechtsetzungsdokumentRahmenMetadata(getMeta());
  }
}
