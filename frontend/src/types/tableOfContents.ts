/**
 * Represents an item in the Table of Contents (ToC).
 */
export interface TabelOfContentsItem {
  /** Unique identifier (EID) of the element */
  id: string
  /** Section marker (e.g., "§ 1") */
  marker: string
  /** Title of the element (optional) */
  heading?: string | null
  /** Type of the element (e.g., article, chapter, etc.) */
  type: string
  /** Nested child elements */
  children?: TabelOfContentsItem[]
}
