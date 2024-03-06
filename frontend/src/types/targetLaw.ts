/**
 * A law to which change commands should be applied.
 */
export interface TargetLaw {
  /**
   * The ELI of the target law
   */
  eli: string
  /**
   * The long title of the target law (akn:docTitle)
   */
  title: string
}
