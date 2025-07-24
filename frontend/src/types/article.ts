import { z } from "zod"

/**
 * Describes an article that is found in a norm
 */
export const ArticleSchema = z.object({
  /** Eid of the article within the law */
  eid: z.string(),
  /** Title of the article */
  title: z.string(),
  /** List position of the article */
  enumeration: z.string(),
})
export type Article = z.infer<typeof ArticleSchema>
