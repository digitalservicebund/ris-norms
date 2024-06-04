import { Proprietary } from "@/types/proprietary"
import { UseFetchReturn } from "@vueuse/core"
import { MaybeRefOrGetter, computed, toValue } from "vue"
import { useApiFetch } from "./apiService"

/**
 * Returns the proprietary metadata of a norm from the API. Reloads when the
 * ELI changes.
 *
 * @param eli ELI of the norm
 * @param options Optional additional filters and queries
 * @returns Reactive fetch wrapper
 */
export function useGetProprietary(
  eli: MaybeRefOrGetter<string | undefined>,
  options?: {
    /**
     * If set, returns the state of the metadata at that date. Otherwise the
     * metadata of the current version will be returned.
     */
    atDate?: MaybeRefOrGetter<string | Date | undefined>
  },
): UseFetchReturn<Proprietary> {
  const dateAsString = computed(() => {
    const atDateVal = toValue(options?.atDate)

    if (!atDateVal) return undefined

    return typeof atDateVal === "string"
      ? atDateVal
      : atDateVal.toISOString().substring(0, 10)
  })

  const url = computed(() => {
    let result = `/norms/${toValue(eli)}/proprietary`
    if (dateAsString.value) result += `/${dateAsString.value}`

    return result
  })

  return useApiFetch<Proprietary>(url, {
    beforeFetch(ctx) {
      if (!toValue(eli)) ctx.cancel()
    },
    refetch: true,
  }).json()
}
