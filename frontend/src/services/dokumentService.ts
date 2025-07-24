import { INVALID_URL, useApiFetch } from "@/services/apiService"
import type { UseFetchOptions, UseFetchReturn } from "@vueuse/core"
import type { MaybeRefOrGetter } from "vue"
import { computed, toValue } from "vue"
import type { DokumentExpressionEli } from "@/lib/eli/DokumentExpressionEli"

/**
 * Returns the dokument from the API. Reloads when the parameters change.
 *
 * @param eli ELI of the dokument
 * @param options Optional additional filters and queries
 * @param [fetchOptions={}] Optional configuration for fetch behavior
 * @returns Reactive fetch wrapper
 */
export function useDokumentService(
  eli: MaybeRefOrGetter<DokumentExpressionEli | undefined>,
  options?: {
    /**
     * Render metadata in the HTML preview. Note that this is only applicable
     * if you get the HTML preview, and will fail on other requests.
     */
    showMetadata?: boolean
  },
  fetchOptions: UseFetchOptions = {},
): UseFetchReturn<unknown> {
  const url = computed(() => {
    const eliVal = toValue(eli)
    if (!eliVal) return INVALID_URL

    const queryParams = new URLSearchParams()

    if (options?.showMetadata) {
      queryParams.append("showMetadata", "true")
    }

    return `/norms/${eliVal}?${queryParams.toString()}`
  })

  return useApiFetch(url, fetchOptions)
}

/**
 * Convenience shorthand for `useDokumentService` that sets the correct
 * configuration for getting the HTML preview.
 *
 * @param eli ELI of the norm
 * @param options Optional additional filters and queries
 * @param [fetchOptions={}] Optional configuration for fetch behavior
 * @returns Reactive fetch wrapper
 */
export function useGetDokumentHtml(
  eli: Parameters<typeof useDokumentService>["0"],
  options?: Parameters<typeof useDokumentService>["1"],
  fetchOptions?: Parameters<typeof useDokumentService>["2"],
): UseFetchReturn<string> {
  return useDokumentService(eli, options, {
    refetch: true,
    ...fetchOptions,
    beforeFetch(c) {
      c.options.headers = { ...c.options.headers, Accept: "text/html" }
    },
  }).text()
}

/**
 * Convenience shorthand for `useDokumentService` that sets the correct
 * configuration for getting the raw XML of the dokument.
 *
 * @param eli ELI of the norm
 * @param options Optional additional filters and queries
 * @param [fetchOptions={}] Optional configuration for fetch behavior
 * @returns Reactive fetch wrapper
 */
export function useGetDokumentXml(
  eli: Parameters<typeof useDokumentService>["0"],
  options?: Parameters<typeof useDokumentService>["1"],
  fetchOptions?: Parameters<typeof useDokumentService>["2"],
): UseFetchReturn<string> & PromiseLike<UseFetchReturn<string>> {
  return useDokumentService(eli, options, {
    refetch: true,
    ...fetchOptions,
    beforeFetch(c) {
      c.options.headers = { ...c.options.headers, Accept: "application/xml" }
    },
  }).text()
}

/**
 * Convenience shorthand for `useDokumentService` that sets the correct
 * configuration for putting the raw XML of the dokument.
 *
 * @param updateData the new xml of the dokument
 * @param eli ELI of the dokument
 * @param options Optional additional filters and queries
 * @param [fetchOptions={}] Optional configuration for fetch behavior
 * @returns Reactive fetch wrapper
 */
export function usePutDokumentXml(
  updateData: MaybeRefOrGetter<string | null | undefined>,
  eli: Parameters<typeof useDokumentService>["0"],
  options?: Parameters<typeof useDokumentService>["1"],
  fetchOptions?: Parameters<typeof useDokumentService>["2"],
): UseFetchReturn<string> {
  return useDokumentService(eli, options, {
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
