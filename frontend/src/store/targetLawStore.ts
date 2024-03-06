import { defineStore } from "pinia"
import { ref, watch } from "vue"
import { getTargetLawByEli, TargetLaw } from "@/services/targetLawsService"

export const useTargetLawStore = defineStore("target-law", () => {
  const targetLaw = ref<TargetLaw | undefined>(undefined)
  const eli = ref<string | undefined>(undefined)
  const loading = ref<boolean>(false)

  function loadTargetLawByEli(newEli: string): void {
    eli.value = newEli
  }

  watch(eli, async (newEli) => {
    if (newEli) {
      if (loading.value) {
        // todo (Malte Laukötter, 2024-03-06): We should cancel a possibly still running call of getTargetLawByEli here. The AbortController-API should allow us to do this.
        console.warn(
          "There is already an unfinished call to getTargetLawByEli, we are still creating a new one but this could have created a race condition.",
        )
      }
      loading.value = true
      targetLaw.value = undefined
      targetLaw.value = await getTargetLawByEli(newEli)
      loading.value = false
    }
  })

  return {
    targetLaw,
    eli,
    loading,
    loadTargetLawByEli,
  }
})
