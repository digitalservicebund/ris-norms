import { z } from "zod"

/** Release information about an verkuendung. */
export const ReleaseSchema = z.object({
  /** Time stamp of when the verkuendung has been released. */
  releaseAt: z.string(),
  /** ELIs of the norm manifestations that are published. */
  norms: z.array(z.string()),
})
export type Release = z.infer<typeof ReleaseSchema>
