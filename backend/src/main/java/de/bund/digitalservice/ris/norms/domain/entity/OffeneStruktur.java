package de.bund.digitalservice.ris.norms.domain.entity;

import lombok.Getter;
import org.w3c.dom.Document;

/**
 * Represents the "OffeneStruktur" of a norm in LDML.de.
 */
@Getter
public non-sealed class OffeneStruktur extends Dokument {

  @Override
  public Dokument copy() {
    return new OffeneStruktur(this);
  }

  public OffeneStruktur(OffeneStruktur offeneStruktur) {
    this((Document) offeneStruktur.getDocument().cloneNode(true));
  }

  public OffeneStruktur(Document document) {
    super(document);
  }
}
