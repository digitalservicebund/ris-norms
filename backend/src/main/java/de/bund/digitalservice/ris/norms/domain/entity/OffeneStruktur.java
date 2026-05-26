package de.bund.digitalservice.ris.norms.domain.entity;

import lombok.Getter;
import org.w3c.dom.Document;

/**
 * Represents the "OffeneStruktur" of a norm in LDML.de.
 */
@Getter
public non-sealed class OffeneStruktur extends Dokument {

  public OffeneStruktur(Document document) {
    super(document);
  }
}
