import { apiFetch, INVALID_URL, useApiFetch } from "@/services/apiService"
import { Norm } from "@/types/norm"
import { UseFetchOptions } from "@vueuse/core"
import { UseFetchReturn } from "@vueuse/core/index"
import { FetchOptions } from "ofetch"
import { computed, MaybeRefOrGetter, toValue, unref } from "vue"

/**
 * Load a norm from the API.
 *
 * @param eli Eli of the amending law
 * @param options Fetch options for the request
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
 * @param options Optional additional filters and queries. This is only a
 *  placeholder for now and can not have a value.
 * @param [fetchOptions={}] Optional configuration for fetch behavior
 * @returns Reactive fetch wrapper
 */
export function useNormService(
  eli: MaybeRefOrGetter<string>,
  options?: never,
  fetchOptions: UseFetchOptions = {},
): UseFetchReturn<Norm> {
  const url = computed(() => {
    const eliVal = toValue(eli)
    if (!eliVal) return INVALID_URL

    return `/norms/${eliVal}`
  })

  return useApiFetch<Norm>(url, fetchOptions).json()
}
