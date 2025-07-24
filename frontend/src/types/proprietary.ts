import { z } from "zod"

/**
 * Encapsulates metadata related to the frame ("Rahmen") that is found in
 * the proprietary section of the document. This includes both metadata that
 * is proprietary to the LDML.de standard and to DigitalService.
 */
export const RahmenProprietarySchema = z.object({
  /** DE "FNA (Fundstellennachweis A)" */
  fna: z.string().optional().nullable(),

  /**
   * Defines the document type when combined with `typ` and `subtyp`. DE "Art"
   */
  art: z.string().optional().nullable(),

  /**
   * Defines the document type when combined with `art` and `subtyp`. DE "Typ"
   */
  typ: z.string().optional().nullable(),

  /**
   * Defines the document type when combined with `art` and `typ`. DE "Subtyp"
   */
  subtyp: z.string().optional().nullable(),

  /** Comma-separated values. DE “Art der Norm" */
  artDerNorm: z.string().optional().nullable(),

  /** DE “Bezeichnung gemäß Vorlage" */
  bezeichnungInVorlage: z.string().optional().nullable(),

  /** DE “Staat, Land, Stadt, Landkreis oder juristische Person, deren Hoheitsgewalt oder Rechtsmacht die Norm trägt" */
  staat: z.string().optional().nullable(),

  /** DE “Beschließendes Organ" */
  beschliessendesOrgan: z.string().optional().nullable(),

  /** DE “Beschlussfassung mit qualifizierter Mehrheit" */
  qualifizierteMehrheit: z.boolean().optional().nullable(),

  /**  DE "Ressort" */
  ressort: z.string().optional().nullable(),

  /** DE "Organisationseinheit" */
  organisationsEinheit: z.string().optional().nullable(),
})
export type RahmenProprietary = z.infer<typeof RahmenProprietarySchema>

/**
 * Encapsulates metadata related to individual elements ("Einzelelemente")
 * that is found in the proprietary section of the document. This includes
 * both metadata that is proprietary to the LDML.de standard and to
 * DigitalService.
 */
export const ElementProprietarySchema = z.object({
  /** DE “Art der Norm" */
  artDerNorm: z.string().optional().nullable(),
})
export type ElementProprietary = z.infer<typeof ElementProprietarySchema>
