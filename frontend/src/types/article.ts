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
  /** The ELI of the law that is changed by this article */
  affectedDocumentEli: string
  /**
   * The ELI of the first future version of the law that is changed
   * by this article.
   */
  affectedDocumentZf0Eli: string
}
