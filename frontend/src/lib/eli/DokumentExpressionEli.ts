import { NormExpressionEli } from "@/lib/eli/NormExpressionEli"
import { NormWorkEli } from "@/lib/eli/NormWorkEli"

/**
 * European legislation identifier on expression level for a Dokument of a Norm
 */
export class DokumentExpressionEli {
  readonly agent: string
  readonly year: string
  readonly naturalIdentifier: string
  readonly pointInTime: string
  readonly version: number
  readonly language: string
  readonly subtype: string

  constructor(
    agent: string,
    year: string,
    naturalIdentifier: string,
    pointInTime: string,
    version: number,
    language: string,
    subtype: string,
  ) {
    this.agent = agent
    this.year = year
    this.naturalIdentifier = naturalIdentifier
    this.pointInTime = pointInTime
    this.version = version
    this.language = language
    this.subtype = subtype
  }

  /**
   * Create an expression level ELI from a string representation
   *
   * @param eli the string representation of the ELI
   * @return the eli
   */
  static fromString(eli: string): DokumentExpressionEli {
    const match =
      /eli\/bund\/(?<agent>[^/]+)\/(?<year>[^/]+)\/(?<naturalIdentifier>[^/]+)\/(?<pointInTime>[^/]+)\/(?<version>[^/]+)\/(?<language>[^/]+)\/(?<subtype>[^/.]+)/.exec(
        eli,
      )

    if (match == null) {
      throw new Error("Invalid Dokument Expression Eli")
    }

    return new DokumentExpressionEli(
      match?.groups?.agent ?? "",
      match?.groups?.year ?? "",
      match?.groups?.naturalIdentifier ?? "",
      match?.groups?.pointInTime ?? "",
      parseInt(match?.groups?.version ?? "0", 10),
      match?.groups?.language ?? "",
      match?.groups?.subtype ?? "",
    )
  }

  /**
   * Create a NormExpressionEli that contains the parts of this ELI
   *
   * @return a norm ELI
   */
  asNormEli(): NormExpressionEli {
    return new NormExpressionEli(
      this.agent,
      this.year,
      this.naturalIdentifier,
      this.pointInTime,
      this.version,
      this.language,
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
    return `eli/bund/${this.agent}/${this.year}/${this.naturalIdentifier}/${this.pointInTime}/${this.version}/${this.language}/${this.subtype}`
  }
}
