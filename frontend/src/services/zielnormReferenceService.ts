import type { NormExpressionEli } from "@/lib/eli/NormExpressionEli"
import { INVALID_URL, useApiFetch } from "@/services/apiService"
import type { ZielnormReference } from "@/types/zielnormReference"
import type { UseFetchOptions, UseFetchReturn } from "@vueuse/core"
import type { MaybeRefOrGetter } from "vue"
import { computed, toValue } from "vue"

/**
 * Shared foundation for interacting with the Zielnormen API.
 *
 * @param eli ELI of the Verkündung
 * @param [fetchOptions={}] Additional options for the fetching
 */
export function useZielnormReferencesService(
  eli: MaybeRefOrGetter<NormExpressionEli | undefined>,
  fetchOptions: UseFetchOptions = {},
): UseFetchReturn<ZielnormReference[]> {
  const url = computed(() => {
    const eliVal = toValue(eli)
    if (!eliVal) return INVALID_URL
    return `/norms/${eliVal}/zielnorm-references`
  })

  return useApiFetch<ZielnormReference[]>(url, { ...fetchOptions })
}

/**
 * Fetches the list of Zielnorm references from the API.
 *
 * @param eli ELI of the Verkündung
 * @param [fetchOptions={}] Additional options for the fetching
 * @returns Reactive fetch wrapper for Zielnormen references
 */
export function useGetZielnormReferences(
  eli: MaybeRefOrGetter<NormExpressionEli | undefined>,
  fetchOptions: UseFetchOptions = {},
): UseFetchReturn<ZielnormReference[]> {
  return useZielnormReferencesService(eli, {
    refetch: true,
    ...fetchOptions,
  }).json()
}

/**
 * Updates the list of Zielnormen references.
 *
 * @param eli ELI of the Verkündung
 * @param [fetchOptions={}] Additional options for the fetching
 * @returns Reactive fetch wrapper for updating Zielnormen references
 */
export function usePostZielnormReferences(
  updateData: MaybeRefOrGetter<ZielnormReference[]>,
  eli: MaybeRefOrGetter<NormExpressionEli | undefined>,
  fetchOptions: UseFetchOptions = {},
): UseFetchReturn<ZielnormReference[]> {
  return useZielnormReferencesService(eli, {
    immediate: false,
    ...fetchOptions,
  })
    .json()
    .post(updateData)
}

/**
 * Deletes Zielnormen references.
 *
 * @param eli ELI of the Verkündung
 * @param [fetchOptions={}] Additional options for the fetching
 * @returns Reactive fetch wrapper for deleting Zielnormen references
 */
export function useDeleteZielnormReferences(
  updateData: MaybeRefOrGetter<string[]>,
  eli: MaybeRefOrGetter<NormExpressionEli | undefined>,
  fetchOptions: UseFetchOptions = {},
): UseFetchReturn<ZielnormReference[]> {
  return useZielnormReferencesService(eli, {
    immediate: false,
    ...fetchOptions,
  })
    .json()
    .delete(updateData)
}
