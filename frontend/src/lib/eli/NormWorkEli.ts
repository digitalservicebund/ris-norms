export const PATH_PARAMETER_JURISDICTION = `Jurisdiction(bund)`
export const PATH_PARAMETER_AGENT = `Agent(bgbl-1|bgbl-2|banz-at)`
export const PATH_PARAMETER_YEAR = `Year([12][0-9]{3})`
export const PATH_PARAMETER_NATURAL_IDENTIFIER = `NaturalIdentifier(s[0-9]+[a-zäöüß]*|[0-9]+-?\\d*)`

/**
 * European legislation identifier on work level for a Norm
 */
export class NormWorkEli {
  readonly agent: string
  readonly year: string
  readonly naturalIdentifier: string

  constructor(agent: string, year: string, naturalIdentifier: string) {
    this.agent = agent
    this.year = year
    this.naturalIdentifier = naturalIdentifier
  }

  /**
   * Create an work level ELI from a string representation
   *
   * @param eli the string representation of the ELI
   * @return the eli
   */
  static fromString(eli: string): NormWorkEli {
    const match =
      /eli\/bund\/(?<agent>[^/]+)\/(?<year>[^/]+)\/(?<naturalIdentifier>[^/]+)/.exec(
        eli,
      )

    if (!match) {
      throw new Error("Invalid work-level ELI")
    }

    return new NormWorkEli(
      match.groups?.agent ?? "",
      match.groups?.year ?? "",
      match.groups?.naturalIdentifier ?? "",
    )
  }

  toString(): string {
    return `eli/bund/${this.agent}/${this.year}/${this.naturalIdentifier}`
  }

  equals(other: NormWorkEli): boolean {
    if (other === this) return true
    else if (!(other instanceof NormWorkEli)) return false
    else return other.toString() === this.toString()
  }
}
