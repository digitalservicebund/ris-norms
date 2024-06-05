import { useApiFetch } from "@/services/apiService"
import { TemporalDataResponse } from "@/types/temporalDataResponse"
import { computed, MaybeRefOrGetter, unref } from "vue"
import { UseFetchReturn } from "@vueuse/core"

/**
 * Fetches the HTML content of an amending law's entry into force section by ELI.
 * This function constructs a query to retrieve documents where the HTML content refers specifically to the "geltungszeitregel"
 *
 * @param eli ELI of the amending law
 * @returns HTML string
 */
export function useGetEntryIntoForceHtml(
  eli: MaybeRefOrGetter<string | undefined>,
): UseFetchReturn<string> {
  const url = computed(() => {
    const eliValue = unref(eli)
    return `/norms/${eliValue}/articles?refersTo=geltungszeitregel`
  })
  return useApiFetch(url.value, {
    headers: {
      Accept: "text/html",
    },
  }).text()
}

/**
 * Fetches the temporal data time boundaries related to an amending law.
 *
 * @returns An Array of TimeBoundary objects each with a date, eventRefEid and temporalGroupEid strings
 */

export function useGetTemporalDataTimeBoundaries(
  eli: MaybeRefOrGetter<string | undefined>,
): UseFetchReturn<TemporalDataResponse[]> {
  const url = computed(() => {
    const eliValue = unref(eli)
    return `/norms/${eliValue}/timeBoundaries`
  })
  return useApiFetch(url.value, {
    method: "GET",
    headers: {
      Accept: "application/json",
    },
  }).json()
}

/**
 * Updates the temporal data time boundaries related to an amending law by ELI.
 *
 * @param eli ELI of the amending law
 * @param dates Array of TimeBoundary objects
 * @returns An updated Array of TimeBoundary objects each with a date, eventRefEid, and temporalgroupEid strings
 * */

export function useUpdateTemporalDataTimeBoundaries(
  eli: MaybeRefOrGetter<string | undefined>,
  dates: TemporalDataResponse[],
): UseFetchReturn<TemporalDataResponse[]> {
  const url = computed(() => {
    const eliValue = unref(eli)
    return `/norms/${eliValue}/timeBoundaries`
  })
  return useApiFetch(url.value, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
      Accept: "application/json",
    },
    body: JSON.stringify(dates),
  }).json()
}
