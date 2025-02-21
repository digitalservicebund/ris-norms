/** Release information about an announcement. */
export interface Release {
  /** Time stamp of when the announcement has been released. */
  releaseAt: string

  /** ELIs of the norm manifestations that are published. */
  norms: string[]
}
