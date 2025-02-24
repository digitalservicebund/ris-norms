import { ElementProprietary, RahmenProprietary } from "@/types/proprietary"
import { UseFetchOptions, UseFetchReturn } from "@vueuse/core"
import { MaybeRefOrGetter, computed, toValue } from "vue"
import { INVALID_URL, useApiFetch } from "./apiService"
import { DokumentExpressionEli } from "@/lib/eli/DokumentExpressionEli"

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
  eli: MaybeRefOrGetter<DokumentExpressionEli | undefined>,
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
  },
  fetchOptions: UseFetchOptions = {},
): UseFetchReturn<RahmenProprietary | ElementProprietary> {
  const url = computed(() => {
    const eliVal = toValue(eli)
    const eidVal = toValue(options.eid)

    if (!eliVal || (eidVal !== null && !eidVal)) {
      return INVALID_URL
    }

    const baseUrl = `/norms/${eliVal}/proprietary`
    return eidVal ? `${baseUrl}/${eidVal}` : baseUrl
  })

  return useApiFetch<RahmenProprietary>(url, fetchOptions)
}

/**
 * Convenience shorthand for `useProprietaryService` that sets the correct
 * configuration for getting JSON data of the frame ("Rahmen", i.e. whole norm).
 *
 * @param eli ELI of the norm
 * @param [fetchOptions={}] Optional configuration for fetch behavior
 * @returns Reactive fetch wrapper
 */
export function useGetRahmenProprietary(
  eli: Parameters<typeof useProprietaryService>["0"],
  fetchOptions?: Parameters<typeof useProprietaryService>["2"],
): UseFetchReturn<RahmenProprietary> {
  return useProprietaryService(
    eli,
    { eid: null },
    { refetch: true, ...fetchOptions },
  ).json()
}

/**
 * Convenience shorthand for `useProprietaryService` that sets the correct
 * configuration for putting JSON data of the frame ("Rahmen", i.e. whole norm).
 *
 * @param updateData The new proprietary data
 * @param eli ELI of the norm
 * @param [fetchOptions={}] Optional configuration for fetch behavior
 * @returns Reactive fetch wrapper
 */
export function usePutRahmenProprietary(
  updateData: MaybeRefOrGetter<RahmenProprietary | null>,
  eli: Parameters<typeof useProprietaryService>["0"],
  fetchOptions?: Parameters<typeof useProprietaryService>["2"],
): UseFetchReturn<RahmenProprietary> {
  return useProprietaryService(
    eli,
    { eid: null },
    { immediate: false, ...fetchOptions },
  )
    .json()
    .put(updateData)
}

/**
 * Convenience shorthand for `useProprietaryService` that sets the correct
 * configuration for getting JSON data of an individual element of the norm.
 *
 * @param eli ELI of the norm
 * @param eid eId of the element
 * @param [fetchOptions={}] Optional configuration for fetch behavior
 * @returns Reactive fetch wrapper
 */
export function useGetElementProprietary(
  eli: Parameters<typeof useProprietaryService>["0"],
  eid: Parameters<typeof useProprietaryService>["1"]["eid"],
  fetchOptions?: Parameters<typeof useProprietaryService>["2"],
): UseFetchReturn<ElementProprietary> {
  return useProprietaryService(
    eli,
    { eid },
    { refetch: true, ...fetchOptions },
  ).json()
}

/**
 * Convenience shorthand for `useProprietaryService` that sets the correct
 * configuration for putting JSON data of an individual element of the norm.
 *
 * @param updateData The new proprietary data
 * @param eli ELI of the norm
 * @param eid eId of the element
 * @param [fetchOptions={}] Optional configuration for fetch behavior
 * @returns Reactive fetch wrapper
 */
export function usePutElementProprietary(
  updateData: MaybeRefOrGetter<ElementProprietary | null>,
  eli: Parameters<typeof useProprietaryService>["0"],
  eid: Parameters<typeof useProprietaryService>["1"]["eid"],
  fetchOptions?: Parameters<typeof useProprietaryService>["2"],
): UseFetchReturn<ElementProprietary> {
  return useProprietaryService(
    eli,
    { eid },
    { immediate: false, ...fetchOptions },
  )
    .json()
    .put(updateData)
}
