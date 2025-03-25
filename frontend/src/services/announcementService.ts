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
  return useApiFetch("/verkuendungen").json()
}

/**
 * Fetch a specific announcement by its ELI.
 *
 * @param eli ELI of the announcement
 */
export function useGetAnnouncementService(
  eli: MaybeRefOrGetter<NormExpressionEli>,
): UseFetchReturn<Announcement> {
  const url = computed(() => `/verkuendungen/${toValue(eli)}`)

  return useApiFetch(url).json()
}

/**
 * Fetch the zielnormen of a specific announcement.
 *
 * @param eli ELI of the announcement
 */
export function useGetZielnormen(
  eli: MaybeRefOrGetter<NormExpressionEli>,
): UseFetchReturn<Norm[]> {
  const url = computed(() => `/verkuendungen/${toValue(eli)}/zielnormen`)
  return useApiFetch(url).json()
}
