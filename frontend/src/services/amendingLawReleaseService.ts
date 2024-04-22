import { apiFetch } from "@/services/apiService"
import { AmendingLawReleaseResponse } from "@/types/amendingLawReleaseResponse"

/**
 * Release the XML of a specific amending law by eli.
 *
 * @param eli Eli of the amending law
 * @returns the publishing timestamp
 * @returns the eli of the amending law
 * @returns the elis of all ZF0
 */
export async function putReleaseAmendingLawXml(
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

export async function getAmendingLawReleaseStatus(
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
