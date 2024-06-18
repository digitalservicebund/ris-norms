import { useApiFetch } from "@/services/apiService"
import { AmendingLawRelease } from "@/types/amendingLawRelease"
import { computed, MaybeRefOrGetter, toValue } from "vue"
import { UseFetchOptions, UseFetchReturn } from "@vueuse/core"

function useReleaseService(
  eli: MaybeRefOrGetter<string>,
  fetchOptions: UseFetchOptions = {},
): UseFetchReturn<AmendingLawRelease> {
  return useApiFetch(
    computed(() => `/announcements/${toValue(eli)}/release`),
    fetchOptions,
  ).json()
}

/**
 * Get the latest release of a specific announcement by eli.
 *
 * @param eli Eli of the norm associated with the announcement
 */
export function useGetRelease(
  eli: MaybeRefOrGetter<string>,
): UseFetchReturn<AmendingLawRelease> {
  return useReleaseService(eli, {
    refetch: true,
  }).get()
}

/**
 * Release the latest release of a specific announcement by eli.
 *
 * @param eli Eli of the norm associated with the announcement
 */
export function usePutRelease(
  eli: MaybeRefOrGetter<string>,
): UseFetchReturn<AmendingLawRelease> {
  return useReleaseService(eli, {
    immediate: false,
  }).put()
}
