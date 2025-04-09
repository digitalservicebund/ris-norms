import { INVALID_URL, useApiFetch } from "@/services/apiService"
import type { TemporalDataResponse } from "@/views/amending-law/articles/editor/temporalDataResponse"
import type { MaybeRefOrGetter } from "vue"
import { computed, ref, toValue, watch } from "vue"
import type { UseFetchReturn } from "@vueuse/core"
import type { DokumentExpressionEli } from "@/lib/eli/DokumentExpressionEli"

/**
 * Fetches the HTML content of a norm's entry into force section by ELI.
 * This function constructs a query to retrieve documents where the HTML content refers specifically to the "geltungszeitregel"
 *
 * @param eli ELI of the norm
 * @returns HTML string
 */
export function useGetEntryIntoForceHtml(
  eli: MaybeRefOrGetter<DokumentExpressionEli | undefined>,
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
 * Fetches the temporal data time boundaries related to a norm.
 *
 * @returns An Array of TimeBoundary objects each with a date, eventRefEid and temporalGroupEid strings
 */
export function useGetTemporalDataTimeBoundaries(
  eli: MaybeRefOrGetter<DokumentExpressionEli | undefined>,
): UseFetchReturn<TemporalDataResponse[]> {
  const url = computed(() => {
    const eliVal = toValue(eli)
    if (!eliVal) return INVALID_URL
    return `/norms/${eliVal}/timeBoundaries`
  })
  return useApiFetch(url, {
    refetch: true,
  })
    .json()
    .get()
}

/**
 * Updates the temporal data time boundaries related to a norm by ELI. Will only fetch on execute.
 *
 * @param eli ELI of the norm
 * @param dates Array of TimeBoundary objects
 * @returns An updated Array of TimeBoundary objects each with a date, eventRefEid, and temporalgroupEid strings
 */
export function useUpdateTemporalDataTimeBoundaries(
  eli: MaybeRefOrGetter<DokumentExpressionEli | undefined>,
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
