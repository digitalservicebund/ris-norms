import { apiFetch } from "@/services/apiService"
import { AmendingLawRelease } from "@/types/amendingLawRelease"

/**
 * Release the XML of a specific announcement by eli.
 *
 * @param eli Eli of the norm associated with the announcement
 */
export async function putRelease(eli: string): Promise<AmendingLawRelease> {
  return await apiFetch(`/announcements/${eli}/release`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/xml",
      Accept: "application/json",
    },
  })
}

/**
 * Release the latest release of a specific announcement by eli.
 *
 * @param eli Eli of the norm associated with the announcement
 */
export async function getRelease(eli: string): Promise<AmendingLawRelease> {
  return await apiFetch(`/announcements/${eli}/release`, {
    method: "GET",
    headers: {
      "Content-Type": "application/xml",
      Accept: "application/json",
    },
  })
}
