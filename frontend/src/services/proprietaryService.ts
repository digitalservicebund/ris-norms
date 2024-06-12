import { Proprietary } from "@/types/proprietary"
import { UseFetchOptions, UseFetchReturn } from "@vueuse/core"
import dayjs from "dayjs"
import { MaybeRefOrGetter, computed, toValue } from "vue"
import { INVALID_URL, useApiFetch } from "./apiService"

/**
 * Returns the proprietary metadata of a norm from the API. Reloads when the
 * parameters change.
 *
 * @param eli ELI of the norm
 * @param options Optional additional filters and queries
 * @param [fetchOptions={}] Optional configuration for fetch behavior
 * @returns Reactive fetch wrapper
 */
export function useProprietaryService(
  eli: MaybeRefOrGetter<string | undefined>,
  options: {
    /**
     * If set, returns the state of the metadata at that date. Otherwise the
     * metadata of the current version will be returned.
     */
    atDate: MaybeRefOrGetter<string | Date | undefined>
  },
  fetchOptions: UseFetchOptions = {},
): UseFetchReturn<Proprietary> {
  const dateAsString = computed(() => {
    const atDateVal = toValue(options?.atDate)

    if (!atDateVal) return undefined

    return typeof atDateVal === "string"
      ? atDateVal
      : dayjs(atDateVal).format("YYYY-MM-DD")
  })

  const url = computed(() => {
    const eliVal = toValue(eli)
    if (!eliVal || !dateAsString.value) return INVALID_URL

    return `/norms/${eliVal}/proprietary/${dateAsString.value}`
  })

  return useApiFetch<Proprietary>(url, fetchOptions)
}

/**
 * Convenience shorthand for `useProprietaryService` that sets the correct
 * configuration for getting JSON data.
 *
 * @param eli ELI of the norm
 * @param options Optional additional filters and queries
 * @param [fetchOptions={}] Optional configuration for fetch behavior
 * @returns Reactive fetch wrapper
 */
export const useGetProprietary: typeof useProprietaryService = (
  eli,
  options,
  fetchOptions,
) => useProprietaryService(eli, options, fetchOptions).json()

/**
 * Convenience shorthand for `useProprietaryService` that sets the correct
 * configuration for putting JSON data.
 *
 * @param eli ELI of the norm
 * @param options Optional additional filters and queries
 * @param [fetchOptions={}] Optional configuration for fetch behavior
 * @returns Reactive fetch wrapper
 */
export function usePutProprietary(
  updateData: MaybeRefOrGetter<Proprietary | null>,
  eli: Parameters<typeof useProprietaryService>["0"],
  options: Parameters<typeof useProprietaryService>["1"],
  fetchOptions: Parameters<typeof useProprietaryService>["2"],
): ReturnType<typeof useProprietaryService> {
  return useProprietaryService(eli, options, fetchOptions)
    .json()
    .put(updateData)
}
