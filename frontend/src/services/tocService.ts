import { INVALID_URL, useApiFetch } from "@/services/apiService"
import type { TocItem } from "@/types/toc"
import type { UseFetchOptions, UseFetchReturn } from "@vueuse/core"
import type { MaybeRefOrGetter } from "vue"
import { computed, toValue } from "vue"
import type { DokumentExpressionEli } from "@/lib/eli/DokumentExpressionEli"

/**
 * Fetches the table of contents (TOC) from the API.
 *
 * @param eli ELI of the norm
 * @param fetchOptions Optional fetch behavior
 * @returns Reactive fetch wrapper for TOC data
 */
export function useGetNormToc(
  eli: MaybeRefOrGetter<DokumentExpressionEli | undefined>,
  fetchOptions: UseFetchOptions = {},
): UseFetchReturn<TocItem[]> {
  const url = computed(() => {
    const eliVal = toValue(eli)
    if (!eliVal) return INVALID_URL
    return `/norms/${eliVal}/toc`
  })

  return useApiFetch<TocItem[]>(url, {
    refetch: true,
    ...fetchOptions,
  }).json()
}
