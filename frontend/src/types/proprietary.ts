/**
 * Encapsulates metadata related to the frame ("Rahmen") that is found in
 * the proprietary section of the document. This includes both metadata that
 * is proprietary to the LDML.de standard and to DigitalService.
 */
export type RahmenProprietary = {
  /** DE "FNA (Fundstellennachweis A)" */
  fna?: string

  /**
   * Defines the document type when combined with `typ` and `subtyp`. DE "Art"
   */
  art?: string

  /**
   * Defines the document type when combined with `art` and `subtyp`. DE "Typ"
   */
  typ?: string

  /**
   * Defines the document type when combined with `art` and `typ`. DE "Subtyp"
   */
  subtyp?: string

  /** Comma-separated values. DE “Art der Norm" */
  artDerNorm?: string

  /** DE “Bezeichnung gemäß Vorlage" */
  bezeichnungInVorlage?: string

  /** DE “Normgeber" */
  normgeber?: string

  /** DE “Beschließendes Organ" */
  beschliessendesOrgan?: string

  /** DE “Beschlussfassung mit qualifizierter Mehrheit" */
  qualifizierteMehrheit?: boolean

  /**  DE "Ressort" */
  ressort?: string

  /** DE "Organisationseinheit" */
  organisationsEinheit?: string
}

/**
 * Encapsulates metadata related to individual elements ("Einzelelemente")
 * that is found in the proprietary section of the document. This includes
 * both metadata that is proprietary to the LDML.de standard and to
 * DigitalService.
 */
export type ElementProprietary = {
  /** DE “Art der Norm" */
  artDerNorm?: string
}
