import {
  useGetTemporalDataTimeBoundaries,
  useUpdateTemporalDataTimeBoundaries,
} from "./temporalDataService"
import type { TemporalDataResponse } from "@/views/amending-law/articles/editor/temporalDataResponse"
import type { MaybeRefOrGetter } from "vue"
import { ref, watch } from "vue"
import type { UseFetchReturn } from "@vueuse/core"
import type { DokumentExpressionEli } from "@/lib/eli/DokumentExpressionEli"

/**
 * Get the temporal data from the API.
 *
 * @param eli ELI of the norm that we want to get the temporal data from
 */
export function useTemporalData(
  eli: MaybeRefOrGetter<DokumentExpressionEli | undefined>,
): UseFetchReturn<TemporalDataResponse[]>
/**
 * Get the temporal data from the API. This also supports updating the temporal data.
 * The data is automatically also updated with the response from the update.
 *
 * @param eli ELI of the norm that we want to get the temporal data from
 * @param newTemporalData a reference to the data that should be saved on an update.
 */
export function useTemporalData(
  eli: MaybeRefOrGetter<DokumentExpressionEli | undefined>,
  newTemporalData: MaybeRefOrGetter<TemporalDataResponse[]>,
): UseFetchReturn<TemporalDataResponse[]> & {
  update: UseFetchReturn<TemporalDataResponse[]>
}
export function useTemporalData(
  eli: MaybeRefOrGetter<DokumentExpressionEli | undefined>,
  newTemporalData?: MaybeRefOrGetter<TemporalDataResponse[]>,
): UseFetchReturn<TemporalDataResponse[]> & {
  update?: UseFetchReturn<TemporalDataResponse[]>
} {
  if (!newTemporalData) {
    return useGetTemporalDataTimeBoundaries(eli)
  }

  // We want to also update the data with the data returned from the PUT-request.
  const data = ref<TemporalDataResponse[] | null>(null)
  const getTemporalDataTimeBoundaries = useGetTemporalDataTimeBoundaries(eli)
  watch(getTemporalDataTimeBoundaries.data, () => {
    data.value = getTemporalDataTimeBoundaries.data.value
  })

  const updateTemporalDataTimeBoundaries = useUpdateTemporalDataTimeBoundaries(
    eli,
    newTemporalData,
  )
  watch(updateTemporalDataTimeBoundaries.data, () => {
    data.value = updateTemporalDataTimeBoundaries.data.value
  })

  return {
    ...getTemporalDataTimeBoundaries,
    data,
    update: updateTemporalDataTimeBoundaries,
  }
}
