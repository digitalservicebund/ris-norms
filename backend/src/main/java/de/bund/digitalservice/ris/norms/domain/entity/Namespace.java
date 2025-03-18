package de.bund.digitalservice.ris.norms.domain.entity;

import lombok.Getter;

/**
 * A XML namespace.
 */
@Getter
public enum Namespace {
  INHALTSDATEN("http://Inhaltsdaten.LegalDocML.de/1.7.2/", "akn"),
  METADATEN("http://Metadaten.LegalDocML.de/1.7.2/", "meta"),
  METADATEN_RIS("http://MetadatenRIS.LegalDocML.de/1.7.2/", "ris"),
  METADATEN_BUNDESREGIERUNG("http://MetadatenBundesregierung.LegalDocML.de/1.7.2/", "meta"),
  METADATEN_NORMS_APPLICATION_MODS("http://MetadatenMods.LegalDocML.de/1.7.2/", "norms");

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
