import { useAmendingLawsStore } from "@/store/loadAmendingLawStore"
import { storeToRefs } from "pinia"
import {
  readonly,
  watch,
  MaybeRefOrGetter,
  toValue,
  Ref,
  DeepReadonly,
} from "vue"
import { AmendingLaw } from "@/services/amendingLawsService"

/**
 * Get the data of an amending law.
 * @param eli a reference to the eli for which the law data will be returned. Changing the value of the reference will load the data for the new eli.
 * @returns A reference to the amending law data or undefined if it is not available (or still loading).
 */
export function useAmendingLaw(
  eli: MaybeRefOrGetter<string>,
): DeepReadonly<Ref<AmendingLaw | undefined>> {
  const amendingLawsStore = useAmendingLawsStore()
  const { loadedAmendingLaw } = storeToRefs(amendingLawsStore)

  watch(
    () => toValue(eli),
    (newEli) => {
      amendingLawsStore.loadAmendingLawByEli(newEli)
    },
    { immediate: true },
  )

  return readonly(loadedAmendingLaw)
}
