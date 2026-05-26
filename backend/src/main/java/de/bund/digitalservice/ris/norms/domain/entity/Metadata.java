package de.bund.digitalservice.ris.norms.domain.entity;

import lombok.Getter;

/**
 * Enum representing all possible data fields present in all different metadata set within {@link Proprietary}
 * <p>
 * Each metadata entry is associated with:
 * <ul>
 *   <li>A {@link Namespace} that defines its XML namespace.</li>
 *   <li>An XPath expression used to locate the metadata within its parent, bound to the namespace.</li>
 *   <li>A tag name that points to the metadata value. This can be the actual tag name or an attribute depending on where the value is stored</li>
 *   <li>An optional attribute flag indicating whether the metadata is an XML attribute.</li>
 * </ul>
 */
@Getter
public enum Metadata {
  // MetadatenDs
  ORGANISATIONS_EINHEIT(Namespace.METADATEN_RIS, "./organisationsEinheit", "organisationsEinheit"),
  AUSSERKRAFT(Namespace.METADATEN_RIS, "./ausserkraft/@date", "date", true),
  INKRAFT(Namespace.METADATEN_RIS, "./inkraft/@date", "date", true);

  private final Namespace namespace;
  private final String xpath;
  private final String tag;
  private final boolean isAttribute;

  Metadata(final Namespace namespace, final String xpath, final String tag) {
    this(namespace, xpath, tag, false);
  }

  Metadata(
    final Namespace namespace,
    final String xpath,
    final String tag,
    final boolean isAttribute
  ) {
    this.namespace = namespace;
    this.xpath = xpath;
    this.tag = tag;
    this.isAttribute = isAttribute;
  }
}
