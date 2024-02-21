import { defineStore } from "pinia"
import { ComputedRef, ref } from "vue"
import {
  getAmendingLaws,
  AmendingLaw,
  getAmendingLawByEli,
} from "@/services/amendingLawsService"

export const useAmendingLawsStore = defineStore("amendingLaws", () => {
  const amendingLaws = ref<AmendingLaw[]>([])
  const selectedAmendingLaw = ref<AmendingLaw>()

  async function loadAmendingLaws() {
    amendingLaws.value = await getAmendingLaws()
  }

  async function loadAmendingLawByEli(eli: ComputedRef<string>) {
    selectedAmendingLaw.value = await getAmendingLawByEli(eli.value)
  }

  return {
    amendingLaws,
    selectedAmendingLaw,
    loadAmendingLaws,
    loadAmendingLawByEli,
  }
})
