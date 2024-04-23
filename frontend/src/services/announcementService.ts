import { apiFetch } from "@/services/apiService"
import { Norm } from "@/types/norm"

/**
 * Load all announcements from the API.
 */
export async function getAmendingLaws(): Promise<Norm[]> {
  return await apiFetch("/announcements")
}
