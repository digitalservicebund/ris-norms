import { apiFetch } from "@/services/apiService"
import { AmendingLawReleaseResponse } from "@/types/amendingLawReleaseResponse"

/**
 * Release the XML of a specific announcement by eli.
 *
 * @param eli Eli of the norm associated with the announcement
 * @returns the publishing timestamp
 * @returns the eli of the amending law
 * @returns the elis of all ZF0
 */
export async function putRelease(
  eli: string,
): Promise<AmendingLawReleaseResponse> {
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
 * @returns the publishing timestamp
 * @returns the eli of the amending law
 * @returns the elis of all ZF0
 */
export async function getRelease(
  eli: string,
): Promise<AmendingLawReleaseResponse> {
  return await apiFetch(`/announcements/${eli}/release`, {
    method: "GET",
    headers: {
      "Content-Type": "application/xml",
      Accept: "application/json",
    },
  })
}
