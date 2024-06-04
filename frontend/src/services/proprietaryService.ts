import { Proprietary } from "@/types/proprietary"
import { UseFetchReturn } from "@vueuse/core"
import { MaybeRefOrGetter, computed, toValue } from "vue"
import { useApiFetch } from "./apiService"

/**
 * Returns the proprietary metadata of a norm from the API. Reloads when the
 * ELI changes.
 *
 * @param eli ELI of the norm
 * @returns Reactive fetch wrapper
 */
export function useGetProprietary(
  eli: MaybeRefOrGetter<string | undefined>,
): UseFetchReturn<Proprietary> {
  const url = computed(() => `/norms/${toValue(eli)}/proprietary`)

  return useApiFetch<Proprietary>(url, {
    beforeFetch(ctx) {
      if (!toValue(eli)) ctx.cancel()
    },
    refetch: true,
  }).json()
}
