import { apiFetch, useApiFetch } from "@/services/apiService"
import { Norm } from "@/types/norm"
import { FetchOptions } from "ofetch"
import { UseFetchReturn } from "@vueuse/core/index"
import { computed, MaybeRefOrGetter, unref } from "vue"

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
 */
export async function getNormXmlByEli(eli: string): Promise<string> {
  return await apiFetch(`/norms/${eli}`, {
    headers: {
      Accept: "application/xml",
    },
  })
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
 * Save the xml version of an norm to the API.
 *
 * @param eli Eli of the norm
 * @param xml New xml of the norm
 * @returns the newly saved xml
 */
export async function putNormXml(eli: string, xml: string) {
  return await apiFetch<string>(`/norms/${eli}`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/xml",
      Accept: "application/xml",
    },
    body: xml,
  })
}
