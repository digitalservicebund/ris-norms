import { z } from "zod"

/**
 * Describes a norm.
 */
export const NormSchema = z.object({
  /** Eli of the norm */
  eli: z.string(),
  /** Title of the law */
  title: z.string().nullable().optional(),
  /** Short title */
  shortTitle: z.string().nullable().optional(),
  /** Fundestellennachweis A */
  fna: z.string().nullable().optional(),
  /** The Amtsblatt that the norm was published in. */
  frbrName: z.string().nullable().optional(),
  /** Fachliche Fundstelle within the Amtsblatt that this norm was published in. */
  frbrNumber: z.string().nullable().optional(),
  /** When the law was published. Conforms to YYYY-MM-DD. */
  frbrDateVerkuendung: z.string().nullable().optional(),
  /** Status of the norm (e.g. 'inForce', 'repealed', etc.) */
  status: z.string().nullable().optional(),
  /** ID of the next version, if available */
  nachfolgendeVersionId: z.string().nullable().optional(),
  /** ID of the previous version, if available */
  vorherigeVersionId: z.string().nullable().optional(),
  /** Is the norm (manifestation) gegenstandslos? */
  gegenstandslos: z.boolean().nullable().optional(),
})

export type Norm = z.infer<typeof NormSchema>
