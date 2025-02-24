export class NormExpressionEli {
  readonly agent: string
  readonly year: string
  readonly naturalIdentifier: string
  readonly pointInTime: string
  readonly version: number
  readonly language: string

  constructor(
    agent: string,
    year: string,
    naturalIdentifier: string,
    pointInTime: string,
    version: number,
    language: string,
  ) {
    this.agent = agent
    this.year = year
    this.naturalIdentifier = naturalIdentifier
    this.pointInTime = pointInTime
    this.version = version
    this.language = language
  }

  static fromString(eli: string): NormExpressionEli {
    const match =
      /eli\/bund\/(?<agent>[^/]+)\/(?<year>[^/]+)\/(?<naturalIdentifier>[^/]+)\/(?<pointInTime>[^/]+)\/(?<version>[^/]+)\/(?<language>[^/]+)/.exec(
        eli,
      )

    if (match == null) {
      throw new Error("Invalid Norm Expression Eli")
    }

    return new NormExpressionEli(
      match.groups?.agent ?? "",
      match.groups?.year ?? "",
      match.groups?.naturalIdentifier ?? "",
      match.groups?.pointInTime ?? "",
      parseInt(match.groups?.version ?? "0", 10),
      match.groups?.language ?? "",
    )
  }

  toString(): string {
    return `eli/bund/${this.agent}/${this.year}/${this.naturalIdentifier}/${this.pointInTime}/${this.version}/${this.language}`
  }
}
