import { z } from "zod"
import { DokumentExpressionEli } from "@/lib/eli/DokumentExpressionEli"

/**
 * Describes an verkuendung.
 */
export const VerkuendungSchema = z.object({
  /** ELI of the verkuendung */
  eli: z.string().transform(DokumentExpressionEli.fromString),
  /** Official long title */
  title: z.string().nullable().optional(),
  /** Short title */
  shortTitle: z.string().nullable().optional(),
  /** Fundstellennachweis (FNA) */
  fna: z.string().nullable().optional(),
  /** Amtsblatt name */
  frbrName: z.string().nullable().optional(),
  /** Amtsblatt number */
  frbrNumber: z.string().nullable().optional(),
  /** Date of publication (Veröffentlichungsdatum) */
  frbrDateVerkuendung: z.string().nullable().optional(),
  /** Date of issue (Ausfertigungsdatum) */
  dateAusfertigung: z.string().nullable().optional(),
  /** Date of first processing (Datenlieferungsdatum) */
  importedAt: z.string().nullable().optional(),
})
export type Verkuendung = z.infer<typeof VerkuendungSchema>
