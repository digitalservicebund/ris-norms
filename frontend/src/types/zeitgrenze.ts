import { z } from "zod"

export const ZeitgrenzeArtSchema = z.enum(["INKRAFT", "AUSSERKRAFT"])
export type ZeitgrenzeArt = z.infer<typeof ZeitgrenzeArtSchema>

export const ZeitgrenzeSchema = z.object({
  /* Unique identifier of the Zeitgrenze */
  id: z.string(),
  /** Date of the Zeitgrenze in the format YYYY-MM-DD */
  date: z.string(),
  /** Type of the change happening at that Zeitgrenze */
  art: ZeitgrenzeArtSchema,
  /**
   * Is the zeitgrenze used by a zielnorm-reference?
   */
  inUse: z.boolean().optional(),
})
export type Zeitgrenze = z.infer<typeof ZeitgrenzeSchema>
