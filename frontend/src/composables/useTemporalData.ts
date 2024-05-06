import { ref, watch, toValue, MaybeRefOrGetter, Ref } from "vue"
import {
  getTemporalDataTimeBoundaries,
  updateTemporalDataTimeBoundaries,
} from "@/services/temporalDataService"
import { TemporalDataReleaseResponse } from "@/types/temporalDataReleaseResponse"

export function useTemporalData(eli: MaybeRefOrGetter<string>): {
  timeBoundaries: Ref<TemporalDataReleaseResponse[]>
  loadData: () => Promise<void>
  updateTemporalData: (
    newTimeBoundaries: TemporalDataReleaseResponse[],
  ) => Promise<void>
} {
  const timeBoundaries = ref<TemporalDataReleaseResponse[]>([])

  async function loadData() {
    try {
      timeBoundaries.value = await getTemporalDataTimeBoundaries(toValue(eli))
    } catch (error) {
      console.error("Error fetching temporal data time boundaries:", error)
    }
  }

  async function updateTemporalData(newDates: TemporalDataReleaseResponse[]) {
    try {
      await updateTemporalDataTimeBoundaries(toValue(eli), newDates)
      timeBoundaries.value = newDates
    } catch (error) {
      console.error("Error updating temporal data:", error)
    }
  }

  watch(() => eli, loadData, { immediate: true })

  return {
    timeBoundaries: timeBoundaries,
    updateTemporalData,
    loadData,
  }
}
