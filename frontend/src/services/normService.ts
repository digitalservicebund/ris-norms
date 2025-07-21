import { INVALID_URL, useApiFetch } from "@/services/apiService"
import type { Norm } from "@/types/norm"
import type { UseFetchOptions, UseFetchReturn } from "@vueuse/core"
import type { MaybeRefOrGetter } from "vue"
import { computed, toValue } from "vue"
import type { NormExpressionEli } from "@/lib/eli/NormExpressionEli"
import type { NormWorkEli } from "@/lib/eli/NormWorkEli"
import { z } from "zod"

/**
 * Returns the norm from the API. Reloads when the parameters change.
 *
 * @param eli ELI of the norm
 * @param options Optional additional filters and queries
 * @param [fetchOptions={}] Optional configuration for fetch behavior
 * @returns Reactive fetch wrapper
 */
export function useNormService(
  eli: MaybeRefOrGetter<NormExpressionEli | undefined>,
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

export const NormWorkSchema = z.object({
  eli: z.string(),
  title: z.string(),
})
export type NormWork = z.infer<typeof NormWorkSchema>

export const NormsPageSchema = z.object({
  content: z.array(NormWorkSchema),
  page: z.object({
    size: z.number(),
    number: z.number(),
    totalElements: z.number(),
    totalPages: z.number(),
  }),
})
export type NormsPage = z.infer<typeof NormsPageSchema>

/**
 * Fetches a paginated list of all norm works from the API.
 * Refetches automatically when the page or size parameters change.
 *
 * @param page The current page number (0-based)
 * @param size The number of items per page
 * @param [fetchOptions={}] Optional configuration for fetch behavior
 * @returns Reactive fetch wrapper with the paginated norms data
 */
export function useGetNorms(
  page: MaybeRefOrGetter<number>,
  size: MaybeRefOrGetter<number>,
  fetchOptions: UseFetchOptions = {},
): UseFetchReturn<NormsPage> {
  const url = computed(() => {
    const queryParams = new URLSearchParams()
    queryParams.append("page", String(toValue(page)))
    queryParams.append("size", String(toValue(size)))
    return `/norms?${queryParams.toString()}`
  })

  return useApiFetch<NormsPage>(url, { refetch: true, ...fetchOptions }).json()
}

/**
 * Fetches a norm work from the API.
 * Refetches automatically when the workEli parameters change.
 *
 * @param workEli The ELI of the norm work
 * @param [fetchOptions={}] Optional configuration for fetch behavior
 * @returns Reactive fetch wrapper with the norm work data
 */
export function useGetNormWork(
  workEli: MaybeRefOrGetter<NormWorkEli | undefined>,
  fetchOptions: UseFetchOptions = {},
): UseFetchReturn<NormWork> {
  const url = computed(() => {
    const eliVal = toValue(workEli)
    if (!eliVal) return INVALID_URL
    return `/norms/${eliVal}`
  })
  return useApiFetch<NormWork>(url, { refetch: true, ...fetchOptions }).json()
}

export const NormExpressionSchema = z.object({
  eli: z.string(),
  gegenstandslos: z.boolean(),
})
export type NormExpression = z.infer<typeof NormExpressionSchema>

/**
 * Fetches the expressions of a norm work from the API.
 * Refetches automatically when the workEli parameters change.
 *
 * @param workEli The ELI of the norm work
 * @param [fetchOptions={}] Optional configuration for fetch behavior
 * @returns Reactive fetch wrapper with the norm expressions data
 */
export function useGetNormExpressions(
  workEli: MaybeRefOrGetter<NormWorkEli | undefined>,
  fetchOptions: UseFetchOptions = {},
): UseFetchReturn<NormExpression[]> {
  const url = computed(() => {
    const eliVal = toValue(workEli)
    if (!eliVal) return INVALID_URL
    return `/norms/${eliVal}/expressions`
  })
  return useApiFetch<NormExpression[]>(url, {
    refetch: true,
    ...fetchOptions,
  }).json()
}
