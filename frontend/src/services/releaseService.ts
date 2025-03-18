import { useApiFetch } from "@/services/apiService"
import type { Release } from "@/types/release"
import type { MaybeRefOrGetter } from "vue"
import { computed, toValue } from "vue"
import type { UseFetchOptions, UseFetchReturn } from "@vueuse/core"
import type { NormExpressionEli } from "@/lib/eli/NormExpressionEli"

function useReleaseService<T>(
  eli: MaybeRefOrGetter<NormExpressionEli>,
  fetchOptions: UseFetchOptions = {},
): UseFetchReturn<T> {
  return useApiFetch(
    computed(() => `/${toValue(eli)}/releases`),
    fetchOptions,
  ).json()
}

/**
 * Get the releases of a specific announcement by eli.
 *
 * @param eli Eli of the norm associated with the announcement
 */
export function useGetReleases(
  eli: MaybeRefOrGetter<NormExpressionEli>,
): UseFetchReturn<Release[]> {
  return useReleaseService<Release[]>(eli, {
    refetch: true,
  }).get()
}

/**
 * Release the latest release of a specific announcement by eli.
 *
 * @param eli Eli of the norm associated with the announcement
 */
export function usePostRelease(
  eli: MaybeRefOrGetter<NormExpressionEli>,
): UseFetchReturn<Release> {
  return useReleaseService<Release>(eli, {
    immediate: false,
  }).post()
}
