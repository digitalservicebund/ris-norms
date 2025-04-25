/**
 * A `norms:zielnorm-reference`. The information about the Zielnorm and
 * Geltungszeit of an amending command, see also: ADR-0015
 */
export type ZielnormReference = {
  /** Typ of the reference (e.g. Änderungsvorschrift) */
  typ: string

  /** ID of the Geltungszeit */
  geltungszeit: string

  /** The eId of the associated amending command */
  eId: string

  /** Work ELI of the Norm that is targeted by the amending command */
  zielnorm: string
}
