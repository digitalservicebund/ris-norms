/** Release information about an verkuendung. */
export interface Release {
  /** Time stamp of when the verkuendung has been released. */
  releaseAt: string

  /** ELIs of the norm manifestations that are published. */
  norms: string[]
}
