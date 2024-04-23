/**
 * Describes a norm.
 */
export interface Norm {
  /** Eli of the norm */
  eli: string
  /**
   * Where the law was published (if published in print)
   * @deprecated use {@link #frbrName} instead
   */
  printAnnouncementGazette?: string
  /**
   * Page where the publication can be found (if published in print)
   * @deprecated use {@link #frbrNumber} instead
   */
  printAnnouncementPage?: string
  /**
   * Where the law was published (if published digitally)
   * @deprecated use {@link #frbrName} instead
   */
  digitalAnnouncementMedium?: string
  /**
   * Edition where the publication can be found (if published digitally)
   * @deprecated use {@link #frbrNumber} instead
   */
  digitalAnnouncementEdition?: string
  /** When the law was published. Conforms to YYYY-MM-DD. */
  publicationDate?: string
  /** Title of the law */
  title?: string
  /** Short title */
  shortTitle?: string
  /** Fundestellennachweis A */
  fna?: string
  /** The Amtsblatt that the norm was published in. */
  frbrName?: string
  /** Fachliche Fundstelle within the Amtsblatt that this norm was published in. */
  frbrNumber?: string
}
