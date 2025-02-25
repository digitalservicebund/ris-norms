import { useApiFetch } from "@/services/apiService"
import { Release } from "@/types/release"
import { computed, MaybeRefOrGetter, toValue } from "vue"
import { UseFetchOptions, UseFetchReturn } from "@vueuse/core"
import { NormExpressionEli } from "@/lib/eli/NormExpressionEli"

function useReleaseService<T>(
  eli: MaybeRefOrGetter<NormExpressionEli>,
  fetchOptions: UseFetchOptions = {},
): UseFetchReturn<T> {
  return useApiFetch(
    computed(() => `/announcements/${toValue(eli)}/releases`),
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
