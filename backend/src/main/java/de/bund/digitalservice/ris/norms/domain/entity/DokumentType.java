package de.bund.digitalservice.ris.norms.domain.entity;

/**
 * The type of a Dokument
 * See LDML.de Section 2.2 "Fachliche Typen der Teildokumente eines Rechtsetzungsdokuments"
 */
public enum DokumentType {
  REGELUNGSTEXT_VERKUENDUNG(
    "/akn/ontology/de/concept/documenttype/bund/regelungstext-verkuendung",
    "regelungstext-verkuendung"
  ),
  ANLAGE_REGELUNGSTEXT(
    "/akn/ontology/de/concept/documenttype/bund/anlage-regelungstext",
    "anlage-regelungstext"
  ),
  RECHTSETZUNGSDOKUMENT(
    "/akn/ontology/de/concept/documenttype/bund/rechtsetzungsdokument",
    "rechtsetzungsdokument"
  ),
  BEKANNTMACHUNGSTEXT(
    "/akn/ontology/de/concept/documenttype/bund/bekanntmachungstext",
    "bekanntmachungstext"
  );

  public final String ontologyUri;
  public final String fileName;

  DokumentType(String ontologyUri, String fileName) {
    this.ontologyUri = ontologyUri;
    this.fileName = fileName;
  }

  /**
   * Find a DokumentType by its ontology uri.
   * @param uri the uri identifying the dokument type
   * @return the dokument type
   */
  public static DokumentType getByOntologyUri(String uri) {
    for (DokumentType dokumentType : DokumentType.values()) {
      if (dokumentType.ontologyUri.equals(uri.toLowerCase())) {
        return dokumentType;
      }
    }

    throw new IllegalArgumentException(
      "DokumentType for ontologyUri '" + uri + "' could not be found"
    );
  }
}
