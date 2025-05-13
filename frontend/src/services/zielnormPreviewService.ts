import type { NormExpressionEli } from "@/lib/eli/NormExpressionEli"
import { INVALID_URL, useApiFetch } from "@/services/apiService"
import type { ZielnormPreview } from "@/types/zielnormPreview"
import type { UseFetchOptions, UseFetchReturn } from "@vueuse/core"
import type { MaybeRefOrGetter } from "vue"
import { computed, toValue } from "vue"

/**
 * Fetches the list of Zielnorm previews from the API.
 *
 * @param eli ELI of the Verkündung
 * @param [fetchOptions={}] Additional options for the fetching
 * @returns Reactive fetch wrapper for Zielnormen references
 */
export function useGetZielnormPreview(
  eli: MaybeRefOrGetter<NormExpressionEli | undefined>,
  fetchOptions: UseFetchOptions = {},
): UseFetchReturn<ZielnormPreview[]> {
  const url = computed(() => {
    const eliVal = toValue(eli)
    if (!eliVal) return INVALID_URL
    return `/verkuendungen/${eliVal}/zielnormen-preview`
  })

  return useApiFetch<ZielnormPreview[]>(url, {
    refetch: true,
    ...fetchOptions,
  }).json()
}
