import { defineStore } from "pinia"
import { ComputedRef, ref } from "vue"
import { getAmendingLaws, AmendingLaw } from "@/services/amendingLawsService"

export const useAmendingLawsStore = defineStore("amendingLaws", () => {
  const amendingLaws = ref<AmendingLaw[]>([])

  async function loadAmendingLaws() {
    amendingLaws.value = await getAmendingLaws()
  }

  function loadAmendingLawByEli(eli: ComputedRef<string>): AmendingLaw {
    return amendingLaws.value.filter(
      (amendingLaw) => amendingLaw.eli == eli.value,
    )[0]
  }

  return { amendingLaws, loadAmendingLaws, loadAmendingLawByEli }
})
