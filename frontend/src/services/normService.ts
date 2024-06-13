import { apiFetch, INVALID_URL, useApiFetch } from "@/services/apiService"
import { Norm } from "@/types/norm"
import { UseFetchOptions, UseFetchReturn } from "@vueuse/core"
import { FetchOptions } from "ofetch"
import { computed, MaybeRefOrGetter, toValue, unref } from "vue"

/**
 * Load a norm from the API.
 *
 * @param eli Eli of the amending law
 * @param options Fetch options for the request
 * @deprecated
 */
export async function getNormByEli(
  eli: string,
  options?: FetchOptions<"json">,
): Promise<Norm> {
  return await apiFetch(`/norms/${eli}`, options)
}

/**
 * Load the xml version of a norm from the API.
 *
 * @param eli Eli of the amending law
 * @deprecated use useNormXml instead
 */
export async function getNormXmlByEli(eli: string): Promise<string> {
  return await apiFetch(`/norms/${eli}`, {
    headers: {
      Accept: "application/xml",
    },
  })
}

/**
 * Composable for loading the xml version of a norm from the API.
 *
 * @param eli Eli of the amending law
 */
export function useGetNormXml(
  eli: MaybeRefOrGetter<string | undefined>,
): UseFetchReturn<string> {
  return useApiFetch(
    computed(() => {
      const eliValue = toValue(eli)
      if (eliValue == undefined) {
        return INVALID_URL
      }
      return `/norms/${eliValue}`
    }),
    {
      headers: {
        Accept: "application/xml",
      },
    },
    {
      refetch: true,
    },
  )
    .text()
    .get()
}

/**
 * Load the rendered html version of a norm from the api
 *
 * @param eli Eli of the norm
 * @param showMetadata Whether to include metadata in the rendered HTML
 * @param at Date indicating which modifications should be applied before the HTML gets rendered and returned
 * @deprecated
 */
export async function getNormHtmlByEli(
  eli: string,
  showMetadata: boolean = false,
  at?: Date,
): Promise<string> {
  return await apiFetch(`/norms/${eli}`, {
    query: {
      showMetadata,
      atIsoDate: at?.toISOString(),
    },
    headers: {
      Accept: "text/html",
    },
  })
}

/**
 * Load the rendered html version of a norm from the api using useFetch
 *
 * @param eli Eli of the norm
 * @param showMetadata Whether to include metadata in the rendered HTML
 * @param at Date indicating which modifications should be applied before the HTML gets rendered and returned
 * @deprecated
 */
export function useGetNormHtmlByEli(
  eli: MaybeRefOrGetter<string | undefined>,
  showMetadata: boolean = false,
  at?: MaybeRefOrGetter<Date | undefined>,
): UseFetchReturn<string> {
  const url = computed(() => {
    const queryParams = new URLSearchParams()
    queryParams.append("showMetadata", String(showMetadata))

    const atValue = unref(at)
    if (atValue instanceof Date) {
      queryParams.append("atIsoDate", atValue.toISOString())
    }

    const eliValue = unref(eli)
    return `/norms/${eliValue}?${queryParams.toString()}`
  })

  return useApiFetch(url.value, {
    headers: {
      Accept: "text/html",
    },
  }).text()
}

/**
 * Create a composable for saving the xml version of a norm to the API.
 *
 * @param eli Eli of the norm
 * @param xml New xml of the norm
 * @returns A UseFetchReturn that includes the methods for executing the PUT request and the response from it.
 */
export function usePutNormXml(
  eli: MaybeRefOrGetter<string | undefined>,
  xml: MaybeRefOrGetter<string | undefined | null>,
): UseFetchReturn<string> {
  return useApiFetch<string>(
    computed(() => {
      const eliValue = toValue(eli)
      if (eliValue == undefined || toValue(xml) == undefined) {
        return INVALID_URL
      }
      return `/norms/${eliValue}`
    }),
    {
      headers: {
        "Content-Type": "application/xml",
        Accept: "application/xml",
      },
    },
    {
      immediate: false,
    },
  ).put(xml)
}

/**
 * Returns the norm from the API. Reloads when the parameters change.
 *
 * @param eli ELI of the norm
 * @param options Optional additional filters and queries
 * @param [fetchOptions={}] Optional configuration for fetch behavior
 * @returns Reactive fetch wrapper
 */
export function useNormService(
  eli: MaybeRefOrGetter<string>,
  options?: {
    /**
     * Render metadata in the HTML preview. Note that this is only applicable
     * if you get the HTML preview, and will fail on other requests.
     */
    showMetadata?: boolean
    /**
     * Render the HTML preview at a specific date. Note that this is only
     * applicable if you get the HTML preview, and will fail on other requests.
     */
    at?: MaybeRefOrGetter<Date | undefined>
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

    if (options?.at) {
      const atVal = toValue(options?.at)
      if (atVal instanceof Date) {
        queryParams.append("atIsoDate", atVal.toISOString())
      }
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
  return useNormService(eli, options, fetchOptions).json()
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
    ...fetchOptions,
    beforeFetch(c) {
      c.options.headers = { ...c.options.headers, Accept: "text/html" }
    },
  }).text()
}
