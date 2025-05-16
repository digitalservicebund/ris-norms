import type { MaybeRefOrGetter } from "vue"
import type { UseFetchOptions, UseFetchReturn } from "@vueuse/core"
import type { Norm } from "@/types/norm"
import { computed, toValue } from "vue"
import { INVALID_URL, useApiFetch } from "@/services/apiService"

/**
 * Load information about a norm by its guid.
 */
export function useNormGuidService(
  guid: MaybeRefOrGetter<string | undefined>,
  fetchOptions: UseFetchOptions = {},
): UseFetchReturn<Norm> & PromiseLike<UseFetchReturn<Norm>> {
  const url = computed(() => {
    const guidVal = toValue(guid)
    if (!guidVal) return INVALID_URL

    return `/norms/${guidVal}`
  })

  return useApiFetch<Norm>(url, {
    refetch: true,
    ...fetchOptions,
  }).json()
}
