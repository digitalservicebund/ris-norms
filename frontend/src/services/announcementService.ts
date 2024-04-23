import { apiFetch } from "@/services/apiService"
import { AmendingLaw } from "@/types/amendingLaw"

/**
 * Load all announcements from the API.
 */
export async function getAmendingLaws(): Promise<AmendingLaw[]> {
  return await apiFetch("/announcements")
}
