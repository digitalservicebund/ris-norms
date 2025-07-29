/**
 * European legislation identifier on manifestation level for a dokument of a norm
 */
export class DokumentManifestationEli {
  readonly agent: string
  readonly year: string
  readonly naturalIdentifier: string
  readonly pointInTime: string
  readonly version: number
  readonly language: string
  readonly pointInTimeManifestation: string | null
  readonly subtype: string
  readonly format: string = "xml"

  constructor(
    agent: string,
    year: string,
    naturalIdentifier: string,
    pointInTime: string,
    version: number,
    language: string,
    pointInTimeManifestation: string | null,
    subtype: string,
    format: string,
  ) {
    this.agent = agent
    this.year = year
    this.naturalIdentifier = naturalIdentifier
    this.pointInTime = pointInTime
    this.version = version
    this.language = language
    this.pointInTimeManifestation = pointInTimeManifestation
    this.subtype = subtype
    this.format = format
  }

  /**
   * Create a manifestation level eli from a string representation
   *
   * @param manifestationEli the string representation of the eli
   * @return the eli
   */
  static fromString(manifestationEli: string): DokumentManifestationEli {
    const matcher =
      /eli\/bund\/(?<agent>[^/]+)\/(?<year>[^/]+)\/(?<naturalIdentifier>[^/]+)\/(?<pointInTime>[^/]+)\/(?<version>[^/]+)\/(?<language>[^/]+)(\/(?<pointInTimeManifestation>[^/.]+))?\/(?<subtype>[^/.]+)\.(?<format>[^/.]+)/.exec(
        manifestationEli,
      )

    if (!matcher?.groups) {
      throw new Error(
        `Invalid Eli: Could not parse DokumentManifestationEli from "${manifestationEli}"`,
      )
    }
    const groups = matcher.groups

    return new DokumentManifestationEli(
      groups.agent,
      groups.year,
      groups.naturalIdentifier,
      groups.pointInTime,
      parseInt(groups.version, 10),
      groups.language,
      groups.pointInTimeManifestation ?? null,
      groups.subtype,
      groups.format,
    )
  }

  /**
   * Does the eli contain a point-in-time-manifestation?
   *
   * @return true if it has a point-in-time-manifestation
   */
  hasPointInTimeManifestation(): boolean {
    return this.pointInTimeManifestation != null
  }

  toString(): string {
    if (!this.hasPointInTimeManifestation()) {
      return `eli/bund/${this.agent}/${this.year}/${this.naturalIdentifier}/${this.pointInTime}/${this.version}/${this.language}/${this.subtype}.${this.format}`
    }

    return `eli/bund/${this.agent}/${this.year}/${this.naturalIdentifier}/${this.pointInTime}/${this.version}/${this.language}/${this.pointInTimeManifestation}/${this.subtype}.${this.format}`
  }

  /**
   * Create a DokumentManifestationEli that contains the parts of this eli but no point-in-time-manifestation
   *
   * @return a manifestation eli without a point-in-time-manifestation
   */
  withoutPointInTimeManifestation(): DokumentManifestationEli {
    if (!this.hasPointInTimeManifestation()) {
      return this
    }

    return new DokumentManifestationEli(
      this.agent,
      this.year,
      this.naturalIdentifier,
      this.pointInTime,
      this.version,
      this.language,
      null,
      this.subtype,
      this.format,
    )
  }

  equals(o: unknown): boolean {
    if (this === o) return true
    if (o == null || !(o instanceof DokumentManifestationEli)) return false
    return this.toString() === o.toString()
  }

  compareTo(o: DokumentManifestationEli): number {
    return this.toString().localeCompare(o.toString())
  }
}
