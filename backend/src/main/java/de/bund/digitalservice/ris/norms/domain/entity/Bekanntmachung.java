package de.bund.digitalservice.ris.norms.domain.entity;

import lombok.Getter;
import org.w3c.dom.Document;

/**
 * Represents the "Bekanntmachung" of a norm in LDML.de.
 */
@Getter
public class Bekanntmachung extends Dokument {

  @Override
  public Dokument copy() {
    return new Bekanntmachung(this);
  }

  public Bekanntmachung(Bekanntmachung bekanntmachung) {
    this((Document) bekanntmachung.getDocument().cloneNode(true));
  }

  public Bekanntmachung(Document document) {
    super(document);
  }
}
