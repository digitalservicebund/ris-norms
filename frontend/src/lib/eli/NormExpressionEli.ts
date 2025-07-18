import { NormWorkEli } from "@/lib/eli/NormWorkEli"

export const PATH_PARAMETER_POINT_IN_TIME = `PointInTime([12][0-9]{3}-[0-9]{2}-[0-9]{2})`
export const PATH_PARAMETER_VERSION = `Version([0-9]+)`
export const PATH_PARAMETER_LANGUAGE = `Language(deu)`

/**
 * European legislation identifier on expression level for a Norm
 */
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

  /**
   * Create an expression level ELI from a string representation
   *
   * @param eli the string representation of the ELI
   * @return the eli
   */
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

  /**
   * Create a NormWorkEli that contains the parts of this ELI
   *
   * @return a norm ELI
   */
  asNormWorkEli(): NormWorkEli {
    return new NormWorkEli(this.agent, this.year, this.naturalIdentifier)
  }

  toString(): string {
    return `eli/bund/${this.agent}/${this.year}/${this.naturalIdentifier}/${this.pointInTime}/${this.version}/${this.language}`
  }
}
