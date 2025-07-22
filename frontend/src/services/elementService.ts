import {
  INVALID_URL,
  type SimpleUseFetchReturn,
  useApiFetch,
} from "@/services/apiService"
import type { Element } from "@/types/element"
import { ElementSchema } from "@/types/element"
import type { UseFetchOptions, UseFetchReturn } from "@vueuse/core"
import type { MaybeRefOrGetter } from "vue"
import { computed, toValue } from "vue"
import type { DokumentExpressionEli } from "@/lib/eli/DokumentExpressionEli"

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
  eli: MaybeRefOrGetter<DokumentExpressionEli | undefined>,
  eid: MaybeRefOrGetter<string | undefined>,
  fetchOptions: UseFetchOptions = {},
): UseFetchReturn<unknown> {
  const url = computed(() => {
    const eliVal = toValue(eli)
    const eidVal = toValue(eid)

    if (!eliVal || !eidVal) {
      return INVALID_URL
    }

    return `/norms/${eliVal}/elements/${eidVal}`
  })

  return useApiFetch<unknown>(url, fetchOptions)
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
export function useGetElement(
  eli: Parameters<typeof useElementService>["0"],
  eid: Parameters<typeof useElementService>["1"],
  fetchOptions: Parameters<typeof useElementService>["2"] = {},
): SimpleUseFetchReturn<Element> {
  const useFetchReturn = useElementService(eli, eid, {
    refetch: true,
    ...fetchOptions,
  }).json<unknown>()
  return {
    ...useFetchReturn,
    data: computed(() =>
      ElementSchema.nullable().parse(useFetchReturn.data.value),
    ),
  }
}

/**
 * Convenience shorthand for `useElementService` that sets the correct
 * configuration for getting HTML data.
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
