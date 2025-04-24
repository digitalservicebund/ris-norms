import type { DokumentExpressionEli } from "@/lib/eli/DokumentExpressionEli"
import { INVALID_URL, useApiFetch } from "@/services/apiService"
import type { TocItem } from "@/types/toc"
import type { UseFetchOptions, UseFetchReturn } from "@vueuse/core"
import type { MaybeRefOrGetter } from "vue"
import { computed, toValue } from "vue"

/**
 * Fetches the list of Zielnormen from the API.
 *
 * @param eli ELI of the Verkündung
 * @returns Reactive fetch wrapper for Zielnormen
 */
export function useGetZielnormen(
  eli: MaybeRefOrGetter<DokumentExpressionEli | undefined>,
  fetchOptions: UseFetchOptions = {},
): UseFetchReturn<TocItem[]> {
  const url = computed(() => {
    const eliVal = toValue(eli)
    if (!eliVal) return INVALID_URL
    return `/norms/${eliVal}/zeitgrenzen`
  })

  return useApiFetch<TocItem[]>(url, {
    refetch: true,
    ...fetchOptions,
  }).json()
}
