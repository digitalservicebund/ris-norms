import { INVALID_URL, useApiFetch } from "@/services/apiService"
import { Norm } from "@/types/norm"
import { UseFetchOptions, UseFetchReturn } from "@vueuse/core"
import { computed, MaybeRefOrGetter, toValue } from "vue"
import { DokumentExpressionEli } from "@/lib/eli/DokumentExpressionEli"

/**
 * Returns the norm from the API. Reloads when the parameters change.
 *
 * @param eli ELI of the norm
 * @param options Optional additional filters and queries
 * @param [fetchOptions={}] Optional configuration for fetch behavior
 * @returns Reactive fetch wrapper
 */
export function useNormService(
  eli: MaybeRefOrGetter<DokumentExpressionEli | undefined>,
  options?: {
    /**
     * Render metadata in the HTML preview. Note that this is only applicable
     * if you get the HTML preview, and will fail on other requests.
     */
    showMetadata?: boolean
  },
  fetchOptions: UseFetchOptions = {},
): UseFetchReturn<Norm> {
  const url = computed(() => {
    const eliVal = toValue(eli)
    if (!eliVal) return INVALID_URL

    const queryParams = new URLSearchParams()

    if (options?.showMetadata) {
      queryParams.append("showMetadata", "true")
    }

    return `/norms/${eliVal}?${queryParams.toString()}`
  })

  return useApiFetch<Norm>(url, fetchOptions)
}

/**
 * Convenience shorthand for `useNormService` that sets the correct
 * configuration for getting JSON data.
 *
 * @param eli ELI of the norm
 * @param options Optional additional filters and queries
 * @param [fetchOptions={}] Optional configuration for fetch behavior
 * @returns Reactive fetch wrapper
 */
export const useGetNorm: typeof useNormService = (
  eli,
  options,
  fetchOptions,
) => {
  return useNormService(eli, options, { refetch: true, ...fetchOptions }).json()
}

/**
 * Convenience shorthand for `useNormService` that sets the correct
 * configuration for getting the HTML preview.
 *
 * @param eli ELI of the norm
 * @param options Optional additional filters and queries
 * @param [fetchOptions={}] Optional configuration for fetch behavior
 * @returns Reactive fetch wrapper
 */
export function useGetNormHtml(
  eli: Parameters<typeof useNormService>["0"],
  options?: Parameters<typeof useNormService>["1"],
  fetchOptions?: Parameters<typeof useNormService>["2"],
): UseFetchReturn<string> {
  return useNormService(eli, options, {
    refetch: true,
    ...fetchOptions,
    beforeFetch(c) {
      c.options.headers = { ...c.options.headers, Accept: "text/html" }
    },
  }).text()
}

/**
 * Convenience shorthand for `useNormService` that sets the correct
 * configuration for getting the raw XML of the norm.
 *
 * @param eli ELI of the norm
 * @param options Optional additional filters and queries
 * @param [fetchOptions={}] Optional configuration for fetch behavior
 * @returns Reactive fetch wrapper
 */
export function useGetNormXml(
  eli: Parameters<typeof useNormService>["0"],
  options?: Parameters<typeof useNormService>["1"],
  fetchOptions?: Parameters<typeof useNormService>["2"],
): UseFetchReturn<string> & PromiseLike<UseFetchReturn<string>> {
  return useNormService(eli, options, {
    refetch: true,
    ...fetchOptions,
    beforeFetch(c) {
      c.options.headers = { ...c.options.headers, Accept: "application/xml" }
    },
  }).text()
}

/**
 * Convenience shorthand for `useNormService` that sets the correct
 * configuration for putting the raw XML of the norm.
 *
 * @param updateData the new xml of the norm
 * @param eli ELI of the norm
 * @param options Optional additional filters and queries
 * @param [fetchOptions={}] Optional configuration for fetch behavior
 * @returns Reactive fetch wrapper
 */
export function usePutNormXml(
  updateData: MaybeRefOrGetter<string | null | undefined>,
  eli: Parameters<typeof useNormService>["0"],
  options?: Parameters<typeof useNormService>["1"],
  fetchOptions?: Parameters<typeof useNormService>["2"],
): UseFetchReturn<string> {
  return useNormService(eli, options, {
    immediate: false,
    ...fetchOptions,
    beforeFetch(c) {
      c.options.headers = {
        ...c.options.headers,
        "Content-Type": "application/xml",
        Accept: "application/xml",
      }
    },
  })
    .text()
    .put(updateData)
}
