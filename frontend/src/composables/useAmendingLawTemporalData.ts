import { ref, onMounted, Ref } from "vue"
import {
  getAmendingLawEntryIntoForceHtml,
  getAmendingLawTemporalDates,
  updateAmendingLawTemporalDates,
} from "@/services/amendingLawEntryIntoForceService"

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
      htmlContent.value = await getAmendingLawEntryIntoForceHtml()
      dates.value = await getAmendingLawTemporalDates()
    } catch (error) {
      console.error("Error fetching amending law data:", error)
    }
  }

  async function update(newDates: string[]) {
    try {
      await updateAmendingLawTemporalDates(eli.value, newDates)
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
