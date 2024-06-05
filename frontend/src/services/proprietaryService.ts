import { Proprietary } from "@/types/proprietary"
import { UseFetchReturn } from "@vueuse/core"
import { MaybeRefOrGetter, computed, toValue } from "vue"
import { INVALID_URL, useApiFetch } from "./apiService"

/**
 * Returns the proprietary metadata of a norm from the API. Reloads when the
 * parameters changes.
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
    const eliVal = toValue(eli)
    if (!eliVal) return INVALID_URL

    let result = `/norms/${toValue(eli)}/proprietary`
    if (dateAsString.value) result += `/${dateAsString.value}`
    return result
  })

  return useApiFetch<Proprietary>(url, {
    beforeFetch({ url, cancel }) {
      if (url === INVALID_URL) cancel()
    },
    refetch: true,
  }).json()
}