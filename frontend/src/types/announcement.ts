/**
 * Describes an announcement.
 */
export interface Announcement {
  /** ELI of the announcement */
  eli: string
  /** Official long title */
  title?: string
  /** Short title */
  shortTitle?: string
  /** Fundstellennachweis (FNA) */
  fna?: string
  /** Amtsblatt name */
  frbrName?: string
  /** Amtsblatt number */
  frbrNumber?: string
  /** Date of publication (Veröffentlichungsdatum). Conforms to YYYY-MM-DD. */
  frbrDateVerkuendung?: string
  /** Date of issue (Ausfertigungsdatum) */
  frbrDateAusfertigung?: string
  /** Date of first processing (Datenlieferungsdatum) */
  importTimestamp?: string
}
