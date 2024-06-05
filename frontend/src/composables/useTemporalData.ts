import {
  useGetTemporalDataTimeBoundaries,
  useUpdateTemporalDataTimeBoundaries,
} from "@/services/temporalDataService"
import { TemporalDataResponse } from "@/types/temporalDataResponse"
import { MaybeRefOrGetter, Ref, ref, toValue, watch } from "vue"

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

  /** Error that occurred while saving. */
  saveError: Ref<Error | null>

  /**
   * Saves temporal data to the API.
   *
   * @param newDates The updated data.
   */
  updateTemporalData: (newDates: TemporalDataResponse[]) => Promise<void>
} {
  const timeBoundaries = ref<TemporalDataResponse[]>([])
  const saveError = ref<Error | null>(null)

  const {
    data: fetchedData,
    error: fetchError,
    isFetching,
  } = useGetTemporalDataTimeBoundaries(toValue(eli))

  watch(
    () => fetchedData.value,
    (newData) => {
      if (newData) {
        timeBoundaries.value = newData
      }
    },
    { immediate: true },
  )

  const updateTemporalData = async (newDates: TemporalDataResponse[]) => {
    const { data: savedData, error: responseSaveError } =
      useUpdateTemporalDataTimeBoundaries(toValue(eli), newDates)
    console.log(savedData)

    watch(
      () => savedData.value,
      (newData) => {
        if (newData) {
          timeBoundaries.value = newData
        }
      },
      { immediate: true },
    )

    watch(
      () => responseSaveError.value,
      (newData) => {
        if (newData) {
          saveError.value = newData
        }
      },
      { immediate: true },
    )
  }

  return {
    timeBoundaries,
    fetchError,
    isFetching,
    saveError,
    updateTemporalData,
  }
}
