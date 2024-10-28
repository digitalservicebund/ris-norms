/** Release information about an amending law. */
export interface AmendingLawRelease {
  /** Time stamp of when the law has been released. */
  releaseAt: string

  /** ELIs of the norm manifestations that are published. */
  norms: string[]
}
