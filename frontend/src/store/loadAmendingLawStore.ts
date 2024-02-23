import { defineStore } from "pinia"
import { ref } from "vue"
import {
  AmendingLaw,
  getAmendingLawByEli,
} from "@/services/amendingLawsService"

export const useAmendingLawsStore = defineStore("loaded-amending-law", () => {
  const loadedAmendingLaw = ref<AmendingLaw | undefined>(undefined)

  async function loadAmendingLawByEli(eli: string): Promise<void> {
    loadedAmendingLaw.value = await getAmendingLawByEli(eli)
  }

  return {
    loadedAmendingLaw,
    loadAmendingLawByEli,
  }
})
