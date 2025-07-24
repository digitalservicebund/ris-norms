import type { Norm } from "@/types/norm"

/**
 * Formats various FRBR information from a norm for displaying them in the UI.
 * Returns `undefined` if required information is missing.
 *
 * @param norm Norm to generate the label for
 * @returns Formatted label or `undefined` if required information is missing
 */
export function getFrbrDisplayText(
  norm:
    | Pick<Norm, "frbrDateVerkuendung" | "frbrNumber" | "frbrName">
    | undefined
    | null,
): string | undefined {
  if (!norm) return undefined

  const publicationYear = norm.frbrDateVerkuendung?.substring(0, 4)
  if (norm.frbrName && norm.frbrNumber) {
    if (norm.frbrNumber.startsWith("s")) {
      return `${norm.frbrName} ${publicationYear} S. ${norm.frbrNumber.replace("s", "")}`
    } else {
      return `${norm.frbrName} ${publicationYear} Nr. ${norm.frbrNumber}`
    }
  }

  return undefined
}
