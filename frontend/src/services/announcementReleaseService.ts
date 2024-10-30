import { useApiFetch } from "@/services/apiService"
import { AmendingLawRelease } from "@/types/amendingLawRelease"
import { computed, MaybeRefOrGetter, toValue } from "vue"
import { UseFetchOptions, UseFetchReturn } from "@vueuse/core"

function useReleaseService<T>(
  eli: MaybeRefOrGetter<string>,
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
  eli: MaybeRefOrGetter<string>,
): UseFetchReturn<AmendingLawRelease[]> {
  return useReleaseService<AmendingLawRelease[]>(eli, {
    refetch: true,
  }).get()
}

/**
 * Release the latest release of a specific announcement by eli.
 *
 * @param eli Eli of the norm associated with the announcement
 */
export function usePostRelease(
  eli: MaybeRefOrGetter<string>,
): UseFetchReturn<AmendingLawRelease> {
  return useReleaseService<AmendingLawRelease>(eli, {
    immediate: false,
  }).post()
}
