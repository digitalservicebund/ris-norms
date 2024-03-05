import { useAmendingLawStore } from "@/store/amendingLawStore"
import { AmendingLaw } from "@/types/domain"
import { storeToRefs } from "pinia"
import {
  DeepReadonly,
  MaybeRefOrGetter,
  Ref,
  readonly,
  toValue,
  watch,
} from "vue"

/**
 * Get the data of an amending law.
 * @param eli a reference to the eli for which the law data will be returned. Changing the value of the reference will load the data for the new eli.
 * @returns A reference to the amending law data or undefined if it is not available (or still loading).
 */
export function useAmendingLaw(
  eli: MaybeRefOrGetter<string>,
): DeepReadonly<Ref<AmendingLaw | undefined>> {
  const amendingLawsStore = useAmendingLawStore()
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
