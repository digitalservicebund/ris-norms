import { INVALID_URL, useApiFetch } from "@/services/apiService"
import { TableOfContentsItem } from "@/types/tableOfContents"
import { UseFetchOptions, UseFetchReturn } from "@vueuse/core"
import { computed, MaybeRefOrGetter, toValue } from "vue"
import { DokumentExpressionEli } from "@/lib/eli/DokumentExpressionEli"

/**
 * Fetches the table of contents (TOC) from the API.
 *
 * @param eli ELI of the norm
 * @param fetchOptions Optional fetch behavior
 * @returns Reactive fetch wrapper for TOC data
 */
export function useGetNormTableOfContents(
  eli: MaybeRefOrGetter<DokumentExpressionEli | undefined>,
  fetchOptions: UseFetchOptions = {},
): UseFetchReturn<TableOfContentsItem[]> {
  const url = computed(() => {
    const eliVal = toValue(eli)
    if (!eliVal) return INVALID_URL
    return `/norms/${eliVal}/toc`
  })

  return useApiFetch<TableOfContentsItem[]>(url, {
    refetch: true,
    ...fetchOptions,
  }).json()
}
