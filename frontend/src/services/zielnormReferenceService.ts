import type { NormExpressionEli } from "@/lib/eli/NormExpressionEli"
import { INVALID_URL, useApiFetch } from "@/services/apiService"
import type { ZielnormReference } from "@/types/zielnormReference"
import type { UseFetchOptions, UseFetchReturn } from "@vueuse/core"
import type { MaybeRefOrGetter } from "vue"
import { computed, toValue } from "vue"

/**
 * Fetches the list of Zielnorm references from the API.
 *
 * @param eli ELI of the Verkündung
 * @returns Reactive fetch wrapper for Zielnormen references
 */
export function useGetZielnormReferences(
  eli: MaybeRefOrGetter<NormExpressionEli | undefined>,
  fetchOptions: UseFetchOptions = {},
): UseFetchReturn<ZielnormReference[]> {
  const url = computed(() => {
    const eliVal = toValue(eli)
    if (!eliVal) return INVALID_URL
    return `/norms/${eliVal}/zielnorm-references`
  })

  return useApiFetch<ZielnormReference[]>(url, {
    refetch: true,
    ...fetchOptions,
  }).json()
}
