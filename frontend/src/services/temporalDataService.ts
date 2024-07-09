import { INVALID_URL, useApiFetch } from "@/services/apiService"
import { TemporalDataResponse } from "@/types/temporalDataResponse"
import { computed, MaybeRefOrGetter, ref, toValue, watch } from "vue"
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
  const url = computed(
    () => `/norms/${toValue(eli)}/articles?refersTo=geltungszeitregel`,
  )
  return useApiFetch(
    url,
    {
      headers: {
        Accept: "text/html",
      },
    },
    {
      refetch: true,
    },
  ).text()
}

/**
 * Fetches the temporal data time boundaries related to an amending law.
 *
 * @returns An Array of TimeBoundary objects each with a date, eventRefEid and temporalGroupEid strings
 */
export function useGetTemporalDataTimeBoundaries(
  eli: MaybeRefOrGetter<string | undefined>,
  options?: {
    /**
     * If set, only returns elements if they are changed by the specified
     * amending law. Should be the ELI of an amending law.
     */
    amendedBy?: MaybeRefOrGetter<string>
  },
): UseFetchReturn<TemporalDataResponse[]> {
  const url = computed(() => {
    const eliVal = toValue(eli)
    if (!eliVal) return INVALID_URL
    const amendedByVal = toValue(options?.amendedBy)
    const query = new URLSearchParams()
    if (amendedByVal) query.append("amendedBy", amendedByVal)
    const queryString = query.toString()
    const baseUrl = `/norms/${eliVal}/timeBoundaries`
    return queryString ? `${baseUrl}?${queryString}` : baseUrl
  })
  return useApiFetch(url, {
    refetch: true,
  })
    .json()
    .get()
}

/**
 * Updates the temporal data time boundaries related to an amending law by ELI. Will only fetch on execute.
 *
 * @param eli ELI of the amending law
 * @param dates Array of TimeBoundary objects
 * @returns An updated Array of TimeBoundary objects each with a date, eventRefEid, and temporalgroupEid strings
 */
export function useUpdateTemporalDataTimeBoundaries(
  eli: MaybeRefOrGetter<string | undefined>,
  dates: MaybeRefOrGetter<TemporalDataResponse[]>,
): UseFetchReturn<TemporalDataResponse[]> {
  const apiFetch: UseFetchReturn<TemporalDataResponse[]> = useApiFetch(
    computed(() => `/norms/${toValue(eli)}/timeBoundaries`),
    {
      immediate: false,
    },
  )
    .json()
    .put(computed(() => JSON.stringify(toValue(dates))))

  // reset isFinished when data changes
  const isFinished = ref(false)
  watch(apiFetch.isFinished, () => {
    isFinished.value = apiFetch.isFinished.value
  })
  watch([() => toValue(eli), () => toValue(dates)], () => {
    isFinished.value = false
  })

  return {
    ...apiFetch,
    isFinished,
  }
}
