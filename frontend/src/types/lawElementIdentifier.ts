/**
 * Encapsulates all required information for adressing a specific element
 * within a law: The ELI for identifying the law, and the eId for elements
 * within that law.
 *
 * This exists as a combined type because most of the time, you will need
 * both, and they are only valid in combination. Should you need only one,
 * feel free to use a simple string instead.
 */
export type LawElementIdentifier = {
  /** ELI of the law. */
  eli: string
  /** eID of some element inside the law. */
  eid: string
}
