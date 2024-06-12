import { Element, ElementType } from "@/types/element"
import { UseFetchOptions, UseFetchReturn } from "@vueuse/core"
import { MaybeRefOrGetter, computed, toValue } from "vue"
import { INVALID_URL, apiFetch, useApiFetch } from "./apiService"

/**
/**
 * Returns a list of elements contained in a norm from the API. Reloads when the
 * parameters change.
 *
 * @param eli ELI of the norm
 * @param types Types of elements that should be included
 * @param options Optional additional filters and queries
 * @param [fetchOptions={}] Optional configuration for fetch behavior
 * @returns Reactive fetch wrapper
 */
export function useElementsService(
  eli: MaybeRefOrGetter<string>,
  types: MaybeRefOrGetter<ElementType[]>,
  options?: {
    /**
     * If set, only returns elements if they are changed by the specified
     * amending law. Should be the ELI of an amending law.
     */
    amendedBy?: MaybeRefOrGetter<string>
  },
  fetchOptions: Pick<UseFetchOptions, "immediate" | "refetch"> = {},
): Pick<
  UseFetchReturn<Element[]>,
  "data" | "error" | "isFetching" | "execute"
> {
  const url = computed(() => {
    const eliVal = toValue(eli)
    if (!eliVal) return INVALID_URL

    const typesVal = toValue(types)
    const amendedByVal = toValue(options?.amendedBy)

    const query = new URLSearchParams()
    typesVal.forEach((type) => query.append("type", type))
    if (amendedByVal) query.append("amendedBy", amendedByVal)

    return `/norms/${eliVal}/elements?${query.toString()}`
  })

  return useApiFetch<Element[]>(url, fetchOptions).json()
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

/**
 * Returns any element that can be identified by its ELI and eId.
 *
 * @param eli ELI of the law containing the element
 * @param eid eId of the element
 * @returns Element inside the norm
 */
export function getElementByEliAndEid(
  eli: string,
  eid: string,
): Promise<Element> {
  return apiFetch<Element>(`/norms/${eli}/elements/${eid}`)
}
