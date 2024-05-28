import {
  getTemporalDataTimeBoundaries,
  updateTemporalDataTimeBoundaries,
} from "@/services/temporalDataService"
import { TemporalDataResponse } from "@/types/temporalDataResponse"
import { MaybeRefOrGetter, Ref, ref, toValue, watch } from "vue"

/**
 * Returns the temporal data from the API and offers methods for interacting
 * with it.
 *
 * @param eli ELI of the norm that we want to get the temporal data from
 * @param options Additional options for how the data is handled
 */
export function useTemporalData(eli: MaybeRefOrGetter<string>): {
  /** Temporal data contained in that norm. */
  timeBoundaries: Ref<TemporalDataResponse[]>

  /** Reloads the data from the API. */
  loadData: () => Promise<void>

  /**
   * Saves temporal data to the API.
   *
   * @param newDates The updated data.
   */
  updateTemporalData: (newDates: TemporalDataResponse[]) => Promise<void>
} {
  const timeBoundaries = ref<TemporalDataResponse[]>([])

  async function loadData() {
    timeBoundaries.value = await getTemporalDataTimeBoundaries(toValue(eli))
  }

  async function updateTemporalData(newDates: TemporalDataResponse[]) {
    const response = await updateTemporalDataTimeBoundaries(
      toValue(eli),
      newDates,
    )

    timeBoundaries.value = response
  }

  watch(() => eli, loadData, { immediate: true })

  return {
    timeBoundaries,
    updateTemporalData,
    loadData,
  }
}
