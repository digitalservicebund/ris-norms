import { useApiFetch } from "@/services/apiService"
import type { UseFetchReturn } from "@vueuse/core"
import type { Norm } from "@/types/norm"
import type { Announcement } from "@/types/announcement"
import type { MaybeRefOrGetter } from "vue"
import { computed, toValue } from "vue"
import type { NormExpressionEli } from "@/lib/eli/NormExpressionEli"

/**
 * Load all announcements from the API.
 */
export function useAnnouncementsService(): UseFetchReturn<Norm[]> {
  return useApiFetch("/announcements").json()
}

/**
 * Fetch a specific announcement by its ELI.
 *
 * @param eli ELI of the announcement
 */
export function useGetAnnouncementService(
  eli: MaybeRefOrGetter<NormExpressionEli>,
): UseFetchReturn<Announcement> {
  const url = computed(() => `/announcements/${toValue(eli)}`)

  return useApiFetch(url).json()
}
