/**
 * Describes an verkuendung.
 */
export interface Verkuendung {
  /** ELI of the verkuendung */
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
  /** Date of publication (Veröffentlichungsdatum) */
  frbrDateVerkuendung?: string
  /** Date of issue (Ausfertigungsdatum) */
  dateAusfertigung?: string
  /** Date of first processing (Datenlieferungsdatum) */
  importedAt?: string
}
