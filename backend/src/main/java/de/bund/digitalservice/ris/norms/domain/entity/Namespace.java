package de.bund.digitalservice.ris.norms.domain.entity;

import lombok.Getter;

/**
 * A XML namespace.
 */
@Getter
public enum Namespace {
  METADATEN_RIS("http://MetadatenRIS.LegalDocML.de/1.8.2/", "ris"),
  METADATEN_BUNDESREGIERUNG("http://MetadatenBundesregierung.LegalDocML.de/1.8.2/", "breg"),
  METADATEN_RECHTSETZUNGSDOKUMENT(
    "http://MetadatenRechtsetzungsdokument.LegalDocML.de/1.8.2/",
    "redok"
  ),
  METADATEN_REGELUNGSTEXT("http://MetadatenRegelungstext.LegalDocML.de/1.8.2/", "regtxt");

  /**
   * The namespace uri of the namespace
   */
  private final String namespaceUri;
  /**
   * The prefix used for elements within that namespace
   */
  private final String prefix;

  Namespace(String namespaceUri, String prefix) {
    this.namespaceUri = namespaceUri;
    this.prefix = prefix;
  }
}
