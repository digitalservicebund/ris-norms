import { ref, watch, toValue, MaybeRefOrGetter, Ref } from "vue"
import {
  getAmendingLawEntryIntoForceHtml,
  getAmendingLawTemporalDataTimeBoundaries,
  updateAmendingLawTemporalDataTimeBoundaries,
} from "@/services/amendingLawTemporalDataService"
import { AmendingLawTemporalDataReleaseResponse } from "@/types/amendingLawTemporalDataReleaseResponse"

interface AmendingLawTemporalData {
  htmlContent: Ref<string>
  timeBoundaries: Ref<AmendingLawTemporalDataReleaseResponse[]>
  update: (
    newTimeBoundaries: AmendingLawTemporalDataReleaseResponse[],
  ) => Promise<void>
  loadData: () => Promise<void>
}

export function useAmendingLawTemporalData(
  eli: MaybeRefOrGetter<string>,
): AmendingLawTemporalData {
  const htmlContent = ref<string>("")
  const timeBoundaries = ref<AmendingLawTemporalDataReleaseResponse[]>([])

  async function loadData() {
    try {
      htmlContent.value = await getAmendingLawEntryIntoForceHtml(toValue(eli))
      timeBoundaries.value = await getAmendingLawTemporalDataTimeBoundaries(
        toValue(eli),
      )
    } catch (error) {
      console.error("Error fetching amending law data:", error)
    }
  }

  async function update(newDates: AmendingLawTemporalDataReleaseResponse[]) {
    try {
      await updateAmendingLawTemporalDataTimeBoundaries(toValue(eli), newDates)
      timeBoundaries.value = newDates
    } catch (error) {
      console.error("Error updating amending law dates:", error)
    }
  }

  watch(
    () => toValue(eli),
    () => loadData(),
    { immediate: true },
  )
  return {
    htmlContent,
    timeBoundaries: timeBoundaries,
    update,
    loadData,
  }
}
