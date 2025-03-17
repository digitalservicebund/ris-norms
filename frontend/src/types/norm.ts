/**
 * Describes a norm.
 */
export interface Norm {
  /** Eli of the norm */
  eli: string
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
  /** When the law was published. Conforms to YYYY-MM-DD. */
  frbrDateVerkuendung?: string
  /** Date of issue (Ausfertigungsdatum) */
  frbrDateAusfertigung?: string
  /** Date of first processing (Datenlieferungsdatum) */
  importTimestamp?: string
}
