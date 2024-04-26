import { ref, onMounted, Ref } from "vue"
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
  eli: Ref<string>,
): AmendingLawTemporalData {
  const htmlContent = ref<string>("")
  const timeBoundaries = ref<AmendingLawTemporalDataReleaseResponse[]>([])

  async function loadData() {
    try {
      htmlContent.value = await getAmendingLawEntryIntoForceHtml(eli.value)
      timeBoundaries.value = await getAmendingLawTemporalDataTimeBoundaries(
        eli.value,
      )
    } catch (error) {
      console.error("Error fetching amending law data:", error)
    }
  }

  async function update(newDates: AmendingLawTemporalDataReleaseResponse[]) {
    try {
      await updateAmendingLawTemporalDataTimeBoundaries(eli.value, newDates)
      timeBoundaries.value = newDates
    } catch (error) {
      console.error("Error updating amending law dates:", error)
    }
  }

  onMounted(loadData)

  return {
    htmlContent,
    timeBoundaries: timeBoundaries,
    update,
    loadData,
  }
}
