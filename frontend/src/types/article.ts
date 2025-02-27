/**
 * Describes an article that is found in a norm
 */
export interface Article {
  /** Eid of the article within the law */
  eid: string
  /** Title of the article */
  title: string
  /** List position of the article */
  enumeration: string
}
