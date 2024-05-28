/** Release information about an amending law. */
export interface AmendingLawRelease {
  /** ELI of the law. */
  amendingLawEli: string

  /** Time stamp of when the law has been released. */
  releaseAt: string

  /** ELIs of the first future versions of the laws changed by this law. */
  zf0Elis: string[]
}
