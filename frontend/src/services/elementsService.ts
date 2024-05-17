import { ElementType, Element } from "@/types/element"
import { apiFetch } from "./apiService"

/**
 * Returns a list of elements inside a law. The type parameter specifies
 * the types of elements that should be returned.
 *
 * @param eli Law from which the elements should be loaded.
 * @param types Types of elements that should be included.
 * @param options Optional additional filters and queries.
 * @returns The list of elements
 */
export function getElementsByEliAndType(
  eli: string,
  types: ElementType[],
  options?: {
    /**
     * If set, only returns elements if they are changed by the specified
     * amending law. Should be the ELI of an amending law.
     */
    amendedBy?: string
  },
): Promise<Element[]> {
  return apiFetch<Element[]>(`/norms/${eli}/elements`, {
    query: { type: types, amendedBy: options?.amendedBy },
  })
}
