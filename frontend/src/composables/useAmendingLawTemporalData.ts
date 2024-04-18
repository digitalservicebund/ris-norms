import { ref, onMounted, Ref } from "vue"
import {
  getAmendingLawEntryIntoForceHtml,
  getAmendingLawTemporalDataIntervals,
  updateAmendingLawTemporalDataIntervals,
} from "@/services/amendingLawTemporalDataService"

interface AmendingLawData {
  htmlContent: Ref<string>
  dates: Ref<string[]>
  update: (newDates: string[]) => Promise<void>
  loadData: () => Promise<void>
}

export function useAmendingLawTemporalData(eli: Ref<string>): AmendingLawData {
  const htmlContent = ref<string>("")
  const dates = ref<string[]>([])

  async function loadData() {
    try {
      htmlContent.value = await getAmendingLawEntryIntoForceHtml(eli.value)
      dates.value = await getAmendingLawTemporalDataIntervals()
    } catch (error) {
      console.error("Error fetching amending law data:", error)
    }
  }

  async function update(newDates: string[]) {
    try {
      await updateAmendingLawTemporalDataIntervals(eli.value, newDates)
      dates.value = newDates
    } catch (error) {
      console.error("Error updating amending law dates:", error)
    }
  }

  onMounted(loadData)

  return {
    htmlContent,
    dates,
    update,
    loadData,
  }
}
