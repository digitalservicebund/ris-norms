import { storeToRefs } from "pinia"
import {
  readonly,
  watch,
  MaybeRefOrGetter,
  toValue,
  Ref,
  DeepReadonly,
} from "vue"
import { useTargetLawStore } from "@/store/targetLawStore"
import { TargetLaw } from "@/services/targetLawsService"

/**
 * Get the data of a target law.
 * @param eli a reference to the eli for which the law data will be returned. Changing the value of the reference will load the data for the new eli.
 * @returns A reference to the target law or undefined if it is not available (or still loading).
 */
export function useTargetLaw(
  eli: MaybeRefOrGetter<string | undefined>,
): DeepReadonly<Ref<TargetLaw | undefined>> {
  const targetLawStore = useTargetLawStore()
  const { targetLaw } = storeToRefs(targetLawStore)

  watch(
    () => toValue(eli),
    (newEli) => {
      if (newEli) {
        targetLawStore.loadTargetLawByEli(newEli)
      }
    },
    { immediate: true },
  )

  return readonly(targetLaw)
}
