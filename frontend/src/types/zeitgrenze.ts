/**
 * Specifies different types of Zeitgrenzen.
 */
export type ZeitgrenzeArt = "INKRAFT" | "AUSSERKRAFT"

/**
 * Describes a Zeitgrenze (= a point in time where changes to a Norm go
 * into effect or expire).
 */
export type Zeitgrenze = {
  /* Unique identifier of the Zeitgrenze */
  id: string

  /** Date of the Zeitgrenze in the format YYYY-MM-DD */
  date: string

  /** Type of the change happening at that Zeitgrenze */
  art: ZeitgrenzeArt

  /**
   * Is the zeitgrenze used by a zielnorm-reference?
   */
  inUse?: boolean
}
