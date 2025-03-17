import { useApiFetch } from "@/services/apiService"
import { UseFetchReturn } from "@vueuse/core"
import { Norm } from "@/types/norm"
import { Announcement } from "@/types/announcement"
import { computed, MaybeRefOrGetter, toValue } from "vue"
import { NormExpressionEli } from "@/lib/eli/NormExpressionEli"

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
  const url = computed(() => `/verkundungen/${toValue(eli)}`)

  return useApiFetch(url).json()
}
