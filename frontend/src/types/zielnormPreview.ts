/**
 * Detail of an expression in the Zielnorm preview.
 */
export type ZielnormPreviewExpression = {
  /** ELI of the expression */
  normExpressionEli: string

  /** Will the expression be gegenstandslos once the zielnorm-references are applied? */
  isGegenstandslos: boolean

  /** Does this expression already exist in the system? */
  isCreated: boolean

  /**
   * Explanation for the reason that this expression will be set to gegenstandslos
   * or be created:
   *
   * - "diese Verkündung" - It's created due to a ZielnormReference that uses this date
   * - "andere Verkündung" - An existing expression, created by a different Verkündung,
   *   that will be set to gegenstandslos
   * - "System" - It's created automatically by the system. Usually when replacing a
   *   now gegenstandslose expression
   */
  createdBy: "diese Verkündung" | "andere Verkündung" | "System"
}

/**
 * Information about a Zielnorm that will be set to gegenstandslos or created
 * when applying a Zielnorm reference of a Verkündung.
 */
export type ZielnormPreview = {
  /** ELI of the Zielnorm */
  normWorkEli: string

  /** Title of the Zielnorm */
  title: string

  /** Short title of the Zielnorm */
  shortTitle: string

  /** List of Expressions of the Zielnorm */
  expressions: ZielnormPreviewExpression[]
}
