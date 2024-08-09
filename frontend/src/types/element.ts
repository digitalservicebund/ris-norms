export type ElementType =
  | "article"
  | "book"
  | "chapter"
  | "conclusions"
  | "part"
  | "preamble"
  | "preface"
  | "subtitle"
  | "title"

/**
 * Describes an element inside a norm.
 */
export type Element = {
  /** Title of the element. May be empty string if no title can be inferred. */
  title: string

  /** eId of the element. */
  eid: string

  /** Node name of the element. */
  type: ElementType
}
