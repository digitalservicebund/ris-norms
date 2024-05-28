import { apiFetch } from "@/services/apiService"
import { TemporalDataResponse } from "@/types/temporalDataResponse"

/**
 * Fetches the HTML content of an amending law's entry into force section by ELI.
 * This function constructs a query to retrieve documents where the HTML content refers specifically to the "geltungszeitregel"
 *
 * @param eli ELI of the amending law
 * @returns HTML string
 */
export async function getEntryIntoForceHtml(eli: string): Promise<string> {
  return await apiFetch(`/norms/${eli}/articles`, {
    headers: {
      Accept: "text/html",
    },
    query: { refersTo: "geltungszeitregel" },
  })
}

/**
 * Fetches the temporal data time boundaries related to an amending law.
 *
 * @returns An Array of TimeBoundary objects each with a date, eventRefEid and temporalGroupEid strings
 */
export async function getTemporalDataTimeBoundaries(
  eli: string,
): Promise<TemporalDataResponse[]> {
  return await apiFetch<TemporalDataResponse[]>(
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
 * Updates the temporal data time boundaries related to an amending law by ELI.
 *
 * @param eli ELI of the amending law
 * @param dates Array of TimeBoundary objects
 * @returns An updated Array of TimeBoundary objects each with a date, eventRefEid, and temporalgroupEid strings
 * */
export async function updateTemporalDataTimeBoundaries(
  eli: string,
  dates: TemporalDataResponse[],
): Promise<TemporalDataResponse[]> {
  return await apiFetch<TemporalDataResponse[]>(
    `/norms/${eli}/timeBoundaries`,
    {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        Accept: "application/json",
      },
      body: JSON.stringify(dates),
    },
  )
}
