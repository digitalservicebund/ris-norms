import { useApiFetch } from "@/services/apiService"
import type { UseFetchReturn } from "@vueuse/core"
import type { Norm } from "@/types/norm"

/**
 * Load all announcements from the API.
 */
export function useAnnouncementsService(): UseFetchReturn<Norm[]> {
  return useApiFetch("/announcements").json()
}
