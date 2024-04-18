// services/amendingLawEntryIntoForceService.js
import { apiFetch } from "@/services/apiService"

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
export async function getAmendingLawTemporalDataIntervals(): Promise<string[]> {
  return [
    "2023-04-01T00:00:00Z",
    "2023-05-15T00:00:00Z",
    "2023-06-20T00:00:00Z",
    "2023-07-25T00:00:00Z",
  ]
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
  dates: string[],
): Promise<string[]> {
  return await apiFetch<string[]>(`/law/${eli}/zeitgrenzen`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
      Accept: "application/json",
    },
    body: JSON.stringify({ dates }),
  })
}
