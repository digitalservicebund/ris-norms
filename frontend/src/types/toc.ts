import { DokumentManifestationEli } from "@/lib/eli/DokumentManifestationEli"
import { z } from "zod"

/**
 * Represents an item in the Table of Contents (ToC).
 */
export const TocItemSchema = z.object({
  /** Unique identifier (EID) of the element */
  id: z.string(),

  /** Section marker (e.g., "§ 1") */
  marker: z.string(),

  /** Title of the element (optional) */
  heading: z.string().nullable().optional(),

  /** Type of the element (e.g., article, chapter, etc.) */
  type: z.string(),

  /** True if the article contains an eingebundene Stammform */
  eingebundeneStammformEli: z
    .string()
    .nullable()
    .transform((arg) => (arg ? DokumentManifestationEli.fromString(arg) : null))
    .optional(),

  /** Nested child elements */
  get children() {
    return z.array(TocItemSchema).optional()
  },
})

export type TocItem = z.infer<typeof TocItemSchema>
