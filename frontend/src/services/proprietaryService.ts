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
     * If set, returns the metadata specifically for the element with that
     * eId within the specified norm. Note that because the endpoint can
     * also return data if the eId is undefined, you need to set eId to
     * `null` explicitly if you only want data from the norm. If the eId
     * is undefined, the service assumes that you intend to load data
     * from an element but the eId is not known yet. In that case the service
     * *will not* load any data in order to avoid loading the wrong data by
     * accident.
     */
    eid: MaybeRefOrGetter<string | undefined> | null

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
    const eidVal = toValue(options.eid)

    if (!eliVal || !dateAsString.value || (eidVal !== null && !eidVal)) {
      return INVALID_URL
    }

    return `/norms/${eliVal}/proprietary/${eidVal ? eidVal + "/" : ""}${dateAsString.value}`
  })

  return useApiFetch<Proprietary>(url, fetchOptions)
}

/**
 * Convenience shorthand for `useProprietaryService` that sets the correct
 * configuration for getting JSON data of the frame (i.e. whole norm).
 *
 * @param eli ELI of the norm
 * @param options Optional additional filters and queries
 * @param [fetchOptions={}] Optional configuration for fetch behavior
 * @returns Reactive fetch wrapper
 */
export function useGetFrameProprietary(
  eli: Parameters<typeof useProprietaryService>["0"],
  options: Omit<Parameters<typeof useProprietaryService>["1"], "eid">,
  fetchOptions?: Parameters<typeof useProprietaryService>["2"],
): ReturnType<typeof useProprietaryService> {
  return useProprietaryService(
    eli,
    { ...options, eid: null },
    { refetch: true, ...fetchOptions },
  ).json()
}

/**
 * Convenience shorthand for `useProprietaryService` that sets the correct
 * configuration for putting JSON data of the frame (i.e. whole norm).
 *
 * @param eli ELI of the norm
 * @param options Optional additional filters and queries
 * @param [fetchOptions={}] Optional configuration for fetch behavior
 * @returns Reactive fetch wrapper
 */
export function usePutFrameProprietary(
  updateData: MaybeRefOrGetter<Proprietary | null>,
  eli: Parameters<typeof useProprietaryService>["0"],
  options: Omit<Parameters<typeof useProprietaryService>["1"], "eid">,
  fetchOptions?: Parameters<typeof useProprietaryService>["2"],
): ReturnType<typeof useProprietaryService> {
  return useProprietaryService(
    eli,
    { ...options, eid: null },
    { immediate: false, ...fetchOptions },
  )
    .json()
    .put(updateData)
}
