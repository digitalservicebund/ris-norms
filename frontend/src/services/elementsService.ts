import { ElementType, Element } from "@/types/element"
import { apiFetch } from "./apiService"

/**
 * Returns a list of elements inside a law. The type parameter specifies
 * the types of elements that should be returned.
 *
 * @param eli Law from which the elements should be loaded
 * @param types Types of elements that should be included
 * @param options Optional additional filters and queries
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

/**
 * Returns any element that can be identified by its ELI and eId as an
 * HTML-rendered preview.
 *
 * @param eli ELI of the law containing the element
 * @param eid eId of the element
 * @param options Optional additional filters and queries
 * @returns String with the rendered HTML of the elment
 */
export function getElementHtmlByEliAndEid(
  eli: string,
  eid: string,
  options?: {
    /** If set, applies all modifications until and including that date. */
    at?: Date
  },
): Promise<string> {
  return apiFetch<string>(`/norms/${eli}/elements/${eid}`, {
    headers: { Accept: "text/html" },
    query: { atIsoDate: options?.at?.toISOString() },
  })
}
