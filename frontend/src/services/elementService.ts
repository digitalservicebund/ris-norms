import { INVALID_URL, useApiFetch } from "@/services/apiService"
import { Element } from "@/types/element"
import { UseFetchOptions, UseFetchReturn } from "@vueuse/core"
import dayjs from "dayjs"
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
 * @param options Optional additional filters and queries
 * @param [fetchOptions={}] Optional configuration for fetch behavior
 * @returns Reactive fetch wrapper
 */
export function useElementService(
  eli: MaybeRefOrGetter<string | undefined>,
  eid: MaybeRefOrGetter<string | undefined>,
  options?: {
    /**
     * If set, applies all modifications until and including that date. Note that
     * this is only applicable if you get the HTML preview, and will fail on other
     * requests.
     */
    at?: MaybeRefOrGetter<Date | undefined>
  },
  fetchOptions: UseFetchOptions = {},
): UseFetchReturn<Element> {
  const url = computed(() => {
    const eliVal = toValue(eli)
    const eidVal = toValue(eid)
    const dateVal = toValue(options?.at)

    if (!eliVal || !eidVal || (dateVal && !dayjs(dateVal).isValid())) {
      return INVALID_URL
    }

    const params = new URLSearchParams()
    if (dateVal) params.set("atIsoDate", dayjs(dateVal).toISOString())

    return `/norms/${eliVal}/elements/${eidVal}?${params.toString()}`
  })

  return useApiFetch(url, fetchOptions)
}

/**
 * Convenience shorthand for `useElementService` that sets the correct
 * configuration for getting JSON data.
 *
 * @param eli ELI of the law containing the element
 * @param eid eId of the element
 * @param options Optional additional filters and queries
 * @param [fetchOptions={}] Optional configuration for fetch behavior
 * @returns Reactive fetch wrapper
 */
export const useGetElement: typeof useElementService = (
  eli,
  eid,
  options,
  fetchOptions,
) =>
  useElementService(eli, eid, options, {
    refetch: true,
    ...fetchOptions,
  }).json()

/**
 * Convenience shorthand for `useElementService` that sets the correct
 * configuration for getting JSON data.
 *
 * @param eli ELI of the law containing the element
 * @param eid eId of the element
 * @param options Optional additional filters and queries
 * @param [fetchOptions={}] Optional configuration for fetch behavior
 * @returns Reactive fetch wrapper
 */
export function useGetElementHtml(
  eli: Parameters<typeof useElementService>["0"],
  eid: Parameters<typeof useElementService>["1"],
  options?: Parameters<typeof useElementService>["2"],
  fetchOptions: Parameters<typeof useElementService>["3"] = {},
): UseFetchReturn<string> {
  return useElementService(eli, eid, options, {
    refetch: true,
    ...fetchOptions,
    beforeFetch(c) {
      c.options.headers = { ...c.options.headers, Accept: "text/html" }
    },
  }).text()
}
