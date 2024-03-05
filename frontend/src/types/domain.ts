/**
 * Describes a specific type of law that is used for changing ("amending")
 * other laws.
 */
export interface AmendingLaw {
  /** Eli of the amending law */
  eli: string
  /** Where the law was published (if published in print) */
  printAnnouncementGazette?: string
  /** Page where the publication can be found (if published in print) */
  printAnnouncementPage?: string
  /** Where the law was published (if published digitally) */
  digitalAnnouncementMedium?: string
  /** Edition where the publication can be found (if published digitally) */
  digitalAnnouncementEdition?: string
  /** When the law was published. Conforms to YYYY-MM-DD. */
  publicationDate: string
  /** Title of the law */
  title?: string
  /**
   * Articles contained in the law.
   * @deprecated Use the article endpoint + AmendingLawArticle
   */
  articles?: AmendingLawArticle[]
}

/**
 * Describes a specific type of article that is found in amending laws
 * and used for changing ("amending") other laws.
 */
export interface AmendingLawArticle {
  /** Eid of the article within the law */
  eid: string
  /** Title of the article */
  title: string
  /** List position of the article */
  enumeration: string
  /** The ELI of the law that is changed by this article */
  affectedDocumentEli: string
}
