import { useApiFetch } from "@/services/apiService"
import { UseFetchReturn } from "@vueuse/core"
import { Norm } from "@/types/norm"

/**
 * Load all announcements from the API.
 */
export function useGetAmendingLaws(): UseFetchReturn<Norm[]> {
  return useApiFetch("/announcements").json()
}
