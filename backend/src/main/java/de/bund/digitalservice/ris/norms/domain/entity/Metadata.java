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
  // MetadatenDe
  FNA(Namespace.METADATEN, "./fna", "fna"),
  ART(Namespace.METADATEN, "./art", "art"),
  TYP(Namespace.METADATEN, "./typ", "typ"),
  GESTA(Namespace.METADATEN, "./gesta", "gesta"),
  FASSUNG(Namespace.METADATEN, "./fassung", "fassung"),

  // MetadatenBund
  FEDERFUEHRUNG(
    Namespace.METADATEN_BUNDESREGIERUNG,
    "./federfuehrung/federfuehrend",
    "federfuehrend",
    false
  ),

  // MetadatenDs
  ART_DER_NORM(Namespace.METADATEN_RIS, "./artDerNorm", "artDerNorm"),
  STAAT(Namespace.METADATEN_RIS, "./normgeber", "normgeber"),
  SUBTYP(Namespace.METADATEN_RIS, "./subtyp", "subtyp"),
  BEZEICHNUNG_IN_VORLAGE(Namespace.METADATEN_RIS, "./bezeichnungInVorlage", "bezeichnungInVorlage"),
  NORMGEBER(Namespace.METADATEN_RIS, "./normgeber", "normgeber"),
  BESCHLIESSENDES_ORGAN(Namespace.METADATEN_RIS, "./beschliessendesOrgan", "beschliessendesOrgan"),
  BESCHLIESSENDES_ORGAN_QUALMEHR(
    Namespace.METADATEN_RIS,
    "./beschliessendesOrgan/@qualifizierteMehrheit",
    "qualifizierteMehrheit",
    true
  ),
  ORGANISATIONS_EINHEIT(Namespace.METADATEN_RIS, "./organisationsEinheit", "organisationsEinheit"),
  EXPIRY_DATE(Namespace.METADATEN_RIS, "./expiry/@date", "date", true),
  ENTRY_INTO_FORCE_DATE(Namespace.METADATEN_RIS, "./entryIntoForce/@date", "date", true),
  STANDANGABE(Namespace.METADATEN_RIS, "./standangabe", "standangabe");

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
