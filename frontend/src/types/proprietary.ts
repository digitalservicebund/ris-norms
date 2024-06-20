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
  artDerNorm?: string

  /**
   * DE “Bezeichnung gemäß Vorlage"
   */
  bezeichnungInVorlage?: string

  /**
   * DE “Normgeber"
   */
  normgeber?: string

  /**
   * DE “Beschließendes Organ"
   */
  beschliessendesOrgan?: string

  /**
   * DE “Beschlussfassung mit qualifizierter Mehrheit"
   */
  qualifizierteMehrheit?: boolean

  /**
   * Federführung (authoring department) of the norm.
   */
  federfuehrung?: string

  /**
   * Organisationseinheit of the norm.
   */
  organisationsEinheit?: string
}
