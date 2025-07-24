import { z } from "zod"
import { NormExpressionEli } from "@/lib/eli/NormExpressionEli"
import { NormWorkEli } from "@/lib/eli/NormWorkEli"

/**
 * Detail of an expression in the Zielnorm preview.
 */
export const ZielnormPreviewExpressionSchema = z.object({
  /** ELI of the expression */
  normExpressionEli: z.string().transform(NormExpressionEli.fromString),

  /** Will the expression be gegenstandslos once the zielnorm-references are applied? */
  isGegenstandslos: z.boolean(),

  /** Does this expression already exist in the system? */
  isCreated: z.boolean(),

  /** Is this expression an orphan? */
  isOrphan: z.boolean(),

  /**
   * Explanation for the reason that this expression will be set to gegenstandslos
   * or be created:
   *
   * - "diese Verkündung" - It's created due to a ZielnormReference that uses this date
   * - "andere Verkündung" - An existing expression, created by a different Verkündung,
   *   that will be set to gegenstandslos
   * - "System" - It's created automatically by the system. Usually when replacing a
   *   now gegenstandslose expression
   */
  createdBy: z.enum(["diese Verkündung", "andere Verkündung", "System"]),
})
export type ZielnormPreviewExpression = z.infer<
  typeof ZielnormPreviewExpressionSchema
>

/**
 * Information about a Zielnorm that will be set to gegenstandslos or created
 * when applying a Zielnorm reference of a Verkündung.
 */
export const ZielnormPreviewSchema = z.object({
  /** ELI of the Zielnorm */
  normWorkEli: z.string().transform(NormWorkEli.fromString),

  /** Title of the Zielnorm */
  title: z.string(),

  /** Short title of the Zielnorm */
  shortTitle: z.string().nullable(),

  /** List of Expressions of the Zielnorm */
  expressions: z.array(ZielnormPreviewExpressionSchema),
})
export type ZielnormPreview = z.infer<typeof ZielnormPreviewSchema>
