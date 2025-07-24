import type { SimpleUseFetchReturn } from "@/services/apiService"
import { INVALID_URL, useApiFetch } from "@/services/apiService"
import type { TocItem } from "@/types/toc"
import { TocItemSchema } from "@/types/toc"
import type { UseFetchOptions } from "@vueuse/core"
import type { MaybeRefOrGetter } from "vue"
import { computed, toValue } from "vue"
import type { DokumentExpressionEli } from "@/lib/eli/DokumentExpressionEli"
import { z } from "zod"

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
): SimpleUseFetchReturn<TocItem[]> {
  const url = computed(() => {
    const eliVal = toValue(eli)
    if (!eliVal) return INVALID_URL
    return `/norms/${eliVal}/toc`
  })

  const useFetchReturn = useApiFetch(url, {
    refetch: true,
    ...fetchOptions,
  }).json<unknown>()

  return {
    ...useFetchReturn,
    data: computed(() =>
      z.array(TocItemSchema).nullable().parse(useFetchReturn.data.value),
    ),
  }
}
