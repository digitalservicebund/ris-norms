import { apiFetch } from "@/services/apiService"
import { AmendingLawTemporalDataReleaseResponse } from "@/types/amendingLawTemporalDataReleaseResponse"

/**
 * Fetches the HTML content of an amending law's entry into force section by ELI.
 * This function constructs a query to retrieve documents where the HTML content refers specifically to the "geltungszeitregel"
 *
 * @param eli ELI of the amending law
 * @returns HTML string
 */
export async function getAmendingLawEntryIntoForceHtml(
  eli: string,
): Promise<string> {
  return await apiFetch(`/norms/${eli}/articles`, {
    headers: {
      Accept: "text/html",
    },
    query: { refersTo: "geltungszeitregel" },
  })
}

/**
 * Fetches the temporal data (zeitgrenzen) related to an amending law.
 *
 * @returns Array of date strings in ISO format
 */
export async function getAmendingLawTemporalDataTimeBoundaries(
  eli: string,
): Promise<AmendingLawTemporalDataReleaseResponse[]> {
  return await apiFetch<AmendingLawTemporalDataReleaseResponse[]>(
    `/norms/${eli}/timeBoundaries`,
    {
      method: "GET",
      headers: {
        Accept: "application/json",
      },
    },
  )
}

/**
 * Updates the temporal data (zeitgrenzen) related to an amending law by ELI.
 *
 * @param eli ELI of the amending law
 * @param dates Array of date strings in ISO format
 * @returns The updated array of dates
 */
export async function updateAmendingLawTemporalDataIntervals(
  eli: string,
  dates: AmendingLawTemporalDataReleaseResponse[],
): Promise<AmendingLawTemporalDataReleaseResponse[]> {
  return await apiFetch<AmendingLawTemporalDataReleaseResponse[]>(
    `/law/${eli}/zeitgrenzen`,
    {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        Accept: "application/json",
      },
      body: JSON.stringify({ dates }),
    },
  )
}
