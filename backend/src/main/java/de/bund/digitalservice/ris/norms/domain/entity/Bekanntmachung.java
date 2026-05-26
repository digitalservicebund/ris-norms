package de.bund.digitalservice.ris.norms.domain.entity;

import lombok.Getter;
import org.w3c.dom.Document;

/**
 * Represents the "Bekanntmachung" of a norm in LDML.de.
 */
@Getter
public non-sealed class Bekanntmachung extends Dokument {

  public Bekanntmachung(Document document) {
    super(document);
  }
}
