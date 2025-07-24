import { z } from "zod"

/**
 * A `norms:zielnorm-reference`. The information about the Zielnorm and
 * Geltungszeit of an amending command, see also: ADR-0015
 */
export const ZielnormReferenceSchema = z.object({
  /** Typ of the reference (e.g. Änderungsvorschrift) */
  typ: z.string(),

  /** ID of the Geltungszeit */
  geltungszeit: z.string(),

  /** The eId of the associated amending command */
  eId: z.string(),

  /** Work ELI of the Norm that is targeted by the amending command */
  zielnorm: z.string(),
})
export type ZielnormReference = z.infer<typeof ZielnormReferenceSchema>
