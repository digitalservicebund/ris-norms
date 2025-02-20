import { INVALID_URL, useApiFetch } from "@/services/apiService"
import { Element } from "@/types/element"
import { UseFetchOptions, UseFetchReturn } from "@vueuse/core"
import { MaybeRefOrGetter, computed, toValue } from "vue"

/* -------------------------------------------------- *
 * Individual elements                                *
 * -------------------------------------------------- */

/**
 * Returns any element that can be identified by its ELI and eId as an
 * HTML-rendered preview. Reloads when the parameters change.
 *
 * @param eli ELI of the law containing the element
 * @param eid eId of the element
 * @param [fetchOptions={}] Optional configuration for fetch behavior
 * @returns Reactive fetch wrapper
 */
export function useElementService(
  eli: MaybeRefOrGetter<string | undefined>,
  eid: MaybeRefOrGetter<string | undefined>,
  fetchOptions: UseFetchOptions = {},
): UseFetchReturn<Element> {
  const url = computed(() => {
    const eliVal = toValue(eli)
    const eidVal = toValue(eid)

    if (!eliVal || !eidVal) {
      return INVALID_URL
    }

    return `/norms/${eliVal}/elements/${eidVal}`
  })

  return useApiFetch(url, fetchOptions)
}

/**
 * Convenience shorthand for `useElementService` that sets the correct
 * configuration for getting JSON data.
 *
 * @param eli ELI of the law containing the element
 * @param eid eId of the element
 * @param [fetchOptions={}] Optional configuration for fetch behavior
 * @returns Reactive fetch wrapper
 */
export const useGetElement: typeof useElementService = (
  eli,
  eid,
  fetchOptions,
) =>
  useElementService(eli, eid, {
    refetch: true,
    ...fetchOptions,
  }).json()

/**
 * Convenience shorthand for `useElementService` that sets the correct
 * configuration for getting JSON data.
 *
 * @param eli ELI of the law containing the element
 * @param eid eId of the element
 * @param [fetchOptions={}] Optional configuration for fetch behavior
 * @returns Reactive fetch wrapper
 */
export function useGetElementHtml(
  eli: Parameters<typeof useElementService>["0"],
  eid: Parameters<typeof useElementService>["1"],
  fetchOptions: Parameters<typeof useElementService>["2"] = {},
): UseFetchReturn<string> {
  return useElementService(eli, eid, {
    refetch: true,
    ...fetchOptions,
    beforeFetch(c) {
      c.options.headers = { ...c.options.headers, Accept: "text/html" }
    },
  }).text()
}
