/**
 * Encapsulates metadata that is found in the proprietary section of the
 * document. This includes both metadata that is proprietary to the LDML.de
 * standard and to DigitalService.
 */
export type Proprietary = {
  /** FNA (Fundstellennachweis A) of a norm. */
  fna?: string

  /**
   * Type of the norm. Defines the document type when combined with `typ` and
   * `subtyp`.
   */
  art?: string

  /**
   * Type of the document. Defines the document type when combined with `art` and
   * `subtyp`.
   */
  typ?: string

  /**
   * Subtype of the document. Defines the document type when combined with `art` and
   * `typ`.
   */
  subtyp?: string

  /**
   * Comma-separated values. DE “Art der Norm"
   */
  artNorm?: string

  /**
   * DE “Bezeichnung gemäß Vorlage"
   */
  bezeichnungInVorlage?: string
}
