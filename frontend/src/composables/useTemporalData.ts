import {
  useGetTemporalDataTimeBoundaries,
  useUpdateTemporalDataTimeBoundaries,
} from "@/services/temporalDataService"
import { TemporalDataResponse } from "@/types/temporalDataResponse"
import { computed, MaybeRefOrGetter, ref, Ref } from "vue"

/**
 * Returns the temporal data from the API and offers methods for interacting
 * with it.
 *
 * @param eli ELI of the norm that we want to get the temporal data from
 */
export function useTemporalData(eli: MaybeRefOrGetter<string | undefined>): {
  /** Temporal data contained in that norm. */
  timeBoundaries: Ref<TemporalDataResponse[]>

  /** Error that occurred while fetching the data. */
  fetchError: Ref<Error | null>

  /** State of Fetching */
  isFetching: Ref<boolean>

  /** Is a request to save temporal data currently running? */
  isSaving: Ref<boolean>

  /** Is a request to save temporal data finished? */
  isSavingFinished: Ref<boolean>

  /** Error that occurred while saving. */
  saveError: Ref<Error | null>

  /**
   * Saves temporal data to the API.
   *
   * @param newDates The updated data.
   */
  saveTemporalData: (newDates: TemporalDataResponse[]) => Promise<void>
} {
  const {
    data: fetchedData,
    error: fetchError,
    isFetching,
  } = useGetTemporalDataTimeBoundaries(eli)

  const newTemporalData = ref<TemporalDataResponse[]>([])
  const {
    data: savedData,
    error: saveError,
    execute: executeSave,
    isFetching: isSaving,
    isFinished: isSavingFinished,
  } = useUpdateTemporalDataTimeBoundaries(eli, newTemporalData)

  const saveTemporalData = async (newDates: TemporalDataResponse[]) => {
    newTemporalData.value = newDates
    await executeSave()
  }

  return {
    timeBoundaries: computed(() => savedData.value ?? fetchedData.value ?? []),
    fetchError,
    saveError,
    isSaving,
    isSavingFinished,
    isFetching,
    saveTemporalData,
  }
}
