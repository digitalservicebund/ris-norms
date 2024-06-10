// Various utilities and helpers for managing proprietary metadata

/* -------------------------------------------------- *
 * Document type                                      *
 * -------------------------------------------------- */

/**
 * Allowed literal values for the "art" proprietary metadata. Please refer
 * to the `meta:art` specification in the LDML.de standard for more information.
 */
export const MetaArtValues = [
  "offene-struktur",
  "rechtsetzungsdokument",
  "regelungstext",
] as const

/** String literal type for allowed values for the "art" proprietary metadata. */
export type MetaArtValue = (typeof MetaArtValues)[number]

/**
 * Type guard ensuring that the provided value is a valid value for Art.
 *
 * @param maybeMetaArt Candidate value
 * @returns Whether the value is of that type
 */
export function isMetaArtValue(
  maybeMetaArt: string | undefined,
): maybeMetaArt is MetaArtValue {
  return !!maybeMetaArt && MetaArtValues.some((value) => value === maybeMetaArt)
}

/**
 * Allowed literal values for the "typ" proprietary metadata. Please refer
 * to the `meta:typ` specification in the LDML.de standard for more information.
 */
export const MetaTypValues = [
  "gesetz",
  "satzung",
  "sonstige-bekanntmachung",
  "verwaltungsvorschrift",
] as const

/** String literal type for allowed values for the "typ" proprietary metadata. */
export type MetaTypValue = (typeof MetaTypValues)[number]

/**
 * Type guard ensuring that the provided value is a valid value for Typ.
 *
 * @param maybeMetaTyp Candidate value
 * @returns Whether the value is of that type
 */
export function isMetaTypValue(
  maybeMetaTyp: string | undefined,
): maybeMetaTyp is MetaTypValue {
  return !!maybeMetaTyp && MetaTypValues.some((value) => value === maybeMetaTyp)
}

/**
 * Allowed literal values for the "subtyp" proprietary metadata.
 */
export const MetaSubtypValues = [
  "Anordnung des Bundespräsidenten",
  "Bekanntmachung vor einer Neufassung",
  "Berichtigung",
  "Beschluss",
  "Durchführungsbestimmung",
  "Geschäftsordnung",
  "Gesetz im formellen Sinne",
  "ohne Qualifikation",
  "Rechtsverordnung",
  "sonstige Anordnung",
  "sonstige Bekanntmachung",
  "Technische Norm",
  "Urteil",
  "Verwaltungsvereinbarung",
  "Verwaltungsvorschrift",
  "Völkerrechtliche Vereinbarung",
] as const

/** String literal type for allowed values for the "subtyp" proprietary metadata. */
export type MetaSubtypValue = (typeof MetaSubtypValues)[number] | null

/**
 * Type guard ensuring that the provided value is a valid value for Typ.
 *
 * @param maybeMetaTyp Candidate value
 * @returns Whether the value is of that type
 */
export function isMetaSubtypValue(
  maybeMetaSubtyp: string | null | undefined,
): maybeMetaSubtyp is MetaSubtypValue {
  return (
    maybeMetaSubtyp === null ||
    (!!maybeMetaSubtyp &&
      MetaSubtypValues.some((value) => value === maybeMetaSubtyp))
  )
}

/**
 * The document type is a combined metadatum that results from the value of
 * `art`, `typ`, and `subtyp`. This record maps possible document types to
 * the combination of values that result in that document type.
 */
export const DocumentTypeValues = {
  "Anordnung des Bundespräsidenten": {
    art: "offene-struktur",
    typ: "sonstige-bekanntmachung",
    subtyp: "Anordnung des Bundespräsidenten",
  },
  "Bekanntmachung vor einer Neufassung": {
    art: "rechtsetzungsdokument",
    typ: "sonstige-bekanntmachung",
    subtyp: "Bekanntmachung vor einer Neufassung",
  },
  Berichtigung: {
    art: "offene-struktur",
    typ: "sonstige-bekanntmachung",
    subtyp: "Berichtigung",
  },
  Beschluss: {
    art: "offene-struktur",
    typ: "sonstige-bekanntmachung",
    subtyp: "Beschluss",
  },
  Durchführungsbestimmung: {
    art: "offene-struktur",
    typ: "sonstige-bekanntmachung",
    subtyp: "Durchführungsbestimmung",
  },
  Geschäftsordnung: {
    art: "offene-struktur",
    typ: "sonstige-bekanntmachung",
    subtyp: "Geschäftsordnung",
  },
  "Gesetz im formellen Sinne": {
    art: "regelungstext",
    typ: "gesetz",
    subtyp: "Gesetz im formellen Sinne",
  },
  "ohne Qualifikation": {
    art: "offene-struktur",
    typ: "sonstige-bekanntmachung",
    subtyp: "ohne Qualifikation",
  },
  Rechtsverordnung: {
    art: "regelungstext",
    typ: "gesetz",
    subtyp: "Rechtsverordnung",
  },
  Satzung: {
    art: "regelungstext",
    typ: "satzung",
    subtyp: null,
  },
  "sonstige Anordnung": {
    art: "offene-struktur",
    typ: "sonstige-bekanntmachung",
    subtyp: "sonstige Anordnung",
  },
  "sonstige Bekanntmachung": {
    art: "rechtsetzungsdokument",
    typ: "sonstige-bekanntmachung",
    subtyp: "sonstige Bekanntmachung",
  },
  "Technische Norm": {
    art: "offene-struktur",
    typ: "sonstige-bekanntmachung",
    subtyp: "Technische Norm",
  },
  Urteil: {
    art: "offene-struktur",
    typ: "sonstige-bekanntmachung",
    subtyp: "Urteil",
  },
  Verwaltungsvereinbarung: {
    art: "regelungstext",
    typ: "verwaltungsvorschrift",
    subtyp: "Verwaltungsvereinbarung",
  },
  Verwaltungsvorschrift: {
    art: "regelungstext",
    typ: "verwaltungsvorschrift",
    subtyp: "Verwaltungsvorschrift",
  },
  "Völkerrechtliche Vereinbarung": {
    art: "offene-struktur",
    typ: "sonstige-bekanntmachung",
    subtyp: "Völkerrechtliche Vereinbarung",
  },
} as const

/** String literal type for allowed values for the document type. */
export type DocumentTypeValue = keyof typeof DocumentTypeValues

/**
 * Takes a combination of metadata and returns the document type that is
 * described by that combination. Can be `undefined` if no document type
 * exists for that combination.
 *
 * @param art Art of the norm
 * @param typ Type of the document
 * @param subtyp Subtype of the document
 * @returns the document type or undefined if there is none for this combination
 */
export function getDocumentTypeFromMetadata(
  art: MetaArtValue,
  typ: MetaTypValue,
  subtyp: MetaSubtypValue,
): DocumentTypeValue | undefined {
  const item = Object.entries(DocumentTypeValues).find(
    ([, metadata]) =>
      metadata.art === art &&
      metadata.typ === typ &&
      metadata.subtyp === subtyp,
  )

  return item?.[0] as DocumentTypeValue
}
