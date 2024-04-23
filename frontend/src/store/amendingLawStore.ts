import { getNormByEli } from "@/services/normService"
import { AmendingLaw } from "@/types/amendingLaw"
import { defineStore } from "pinia"
import { ref, watch } from "vue"

export const useAmendingLawStore = defineStore("amending-law", () => {
  const loadedAmendingLaw = ref<AmendingLaw | undefined>(undefined)
  const eli = ref<string | undefined>(undefined)
  const loading = ref<boolean>(false)

  function loadAmendingLawByEli(newEli: string): void {
    eli.value = newEli
  }

  watch(eli, async (newEli) => {
    if (newEli) {
      if (loading.value) {
        // todo (Malte Laukötter, 2024-02-26): We should cancel a possibly still running call of getAmendingLawByEli here. The AbortController-API should allow us to do this.
        console.warn(
          "There is already an unfinished call to getAmendingLawByEli, we are still creating a new one but this could have created a race condition.",
        )
      }
      loading.value = true
      loadedAmendingLaw.value = undefined
      loadedAmendingLaw.value = await getNormByEli(newEli)
      loading.value = false
    }
  })

  return {
    loadedAmendingLaw,
    eli,
    loading,
    loadAmendingLawByEli,
  }
})
