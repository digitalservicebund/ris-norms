import { defineStore } from "pinia"
import { ref, watch } from "vue"
import { Norm } from "@/types/norm"
import { getNormByEli } from "@/services/normService"

/**
 * Store that provides access to a single target law.
 *
 * Whenever loadTargetLawByEli is called with a new eli all places that use this store will than use the data about this
 * new law. The store only ever provides a single law.
 */
export const useTargetLawStore = defineStore("target-law", () => {
  /**
   * The current target law or undefined when it is not yet loaded or does not exist.
   */
  const targetLaw = ref<Norm | undefined>(undefined)
  /**
   * The eli of the current target law.
   */
  const eli = ref<string | undefined>(undefined)
  /**
   * Indicator if a target law is currently loading.
   */
  const loading = ref<boolean>(false)

  /**
   * Load a new target law.
   *
   * This will affect all places that use this store.
   *
   * @param newEli
   */
  function loadTargetLawByEli(newEli: string): void {
    eli.value = newEli
  }

  watch(eli, async (newEli, oldEli, onCleanup) => {
    if (newEli) {
      loading.value = true
      targetLaw.value = undefined

      const abortController = new AbortController()
      onCleanup(() => {
        abortController.abort()
        loading.value = false
      })

      targetLaw.value = await getNormByEli(newEli, {
        signal: abortController.signal,
      })

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
