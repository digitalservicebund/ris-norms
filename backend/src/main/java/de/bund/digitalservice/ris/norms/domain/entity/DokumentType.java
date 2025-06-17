package de.bund.digitalservice.ris.norms.domain.entity;

/**
 * The type of a Dokument
 * See LDML.de Section 2.2 "Fachliche Typen der Teildokumente eines Rechtsetzungsdokuments"
 */
public enum DokumentType {
  REGELUNGSTEXT_VERKUENDUNG("/akn/ontology/de/concept/documenttype/bund/regelungstext-verkuendung"),
  ANLAGE_REGELUNGSTEXT("/akn/ontology/de/concept/documenttype/bund/anlage-regelungstext"),
  RECHTSETZUNGSDOKUMENT("/akn/ontology/de/concept/documenttype/bund/rechtsetzungsdokument"),
  BEKANNTMACHUNGSTEXT("/akn/ontology/de/concept/documenttype/bund/bekanntmachungstext");

  public final String ontologyUri;

  DokumentType(String ontologyUri) {
    this.ontologyUri = ontologyUri;
  }
}
