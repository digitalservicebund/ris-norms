export class NormWorkEli {
  readonly agent: string
  readonly year: string
  readonly naturalIdentifier: string

  constructor(agent: string, year: string, naturalIdentifier: string) {
    this.agent = agent
    this.year = year
    this.naturalIdentifier = naturalIdentifier
  }

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
}
