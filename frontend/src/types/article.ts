/**
 * Describes a specific type of article that is found in amending laws
 * and used for changing ("amending") other laws.
 */
export interface Article {
  /** Eid of the article within the law */
  eid: string
  /** Title of the article */
  title: string
  /** List position of the article */
  enumeration: string
}
