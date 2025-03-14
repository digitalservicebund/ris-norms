/**
 * Describes an announcement.
 */
export interface Announcement {
  /** ELI of the announcement */
  eli: string
  /** Official long title */
  title: string
  /** Date of publication */
  veroeffentlichungsdatum: string
  /** Date of issue */
  ausfertigungsdatum: string
  /** Date of first processing */
  datenlieferungsdatum: string
  /** Fundstellennachweis (FNA) */
  fna?: string
}
