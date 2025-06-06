package de.bund.digitalservice.ris.norms.domain.entity;

import lombok.Getter;

/**
 * A XML namespace.
 */
@Getter
public enum Namespace {
  INHALTSDATEN("http://Inhaltsdaten.LegalDocML.de/1.8/", "akn"),
  METADATEN_RIS("http://MetadatenRIS.LegalDocML.de/1.8/", "ris"),
  METADATEN_BUNDESREGIERUNG("http://MetadatenBundesregierung.LegalDocML.de/1.8/", "meta"),
  METADATEN_NORMS_APPLICATION_MODS("http://MetadatenMods.LegalDocML.de/1.8/", "norms"),
  METADATEN_RECHTSETZUNGSDOKUMENT(
    "http://MetadatenRechtsetzungsdokument.LegalDocML.de/1.8/",
    "redok"
  ),
  METADATEN_REGELUNGSTEXT("http://MetadatenRegelungstext.LegalDocML.de/1.8/", "regtxt");

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

  /**
   * Find a namespace by its URI.
   * @param uri the uri identifying the namespace
   * @return the namespace
   */
  public static Namespace getByUri(String uri) {
    for (Namespace namespace : Namespace.values()) {
      if (namespace.getNamespaceUri().equals(uri)) {
        return namespace;
      }
    }

    throw new IllegalArgumentException("Namespace '" + uri + "' could not be found");
  }
}
