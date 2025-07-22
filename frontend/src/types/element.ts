import { z } from "zod"

export const ElementTypeSchema = z.enum([
  "article",
  "book",
  "chapter",
  "conclusions",
  "part",
  "preamble",
  "preface",
  "section",
  "subsection",
  "subtitle",
  "title",
])
export type ElementType = z.infer<typeof ElementTypeSchema>

/**
 * Describes an element inside a norm.
 */
export const ElementSchema = z.object({
  /** Title of the element. May be empty string if no title can be inferred. */
  title: z.string(),
  /** eId of the element. */
  eid: z.string(),
  /** Node name of the element. */
  type: ElementTypeSchema,
})
export type Element = z.infer<typeof ElementSchema>
