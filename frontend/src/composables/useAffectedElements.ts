import { getElementsByEliAndType } from "@/services/elementService"
import { Element, ElementType } from "@/types/element"
import {
  DeepReadonly,
  MaybeRefOrGetter,
  Ref,
  readonly,
  ref,
  toValue,
  watchEffect,
} from "vue"

/**
 * Get all elements in a law that have pending changes (aka. passive modifications).
 * Elements can be filtered by a time boundary on which the change takes effect, the
 * law making the change, or both.
 *
 * @param eli A reference to the ELI of the law for which the element data
 *  will be returned. Changing the value of the reference will load the data for the
 *  new ELI.
 * @param types A reference to the types of the elements that should be loaded.
 *  Changing the value of the reference will load the data for the new types.
 * @param amendingLawEli A reference to the ELI of the amending law for which the
 *  changed elements should be loaded. Changing the value of the reference will load
 *  the data for the new eId.
 * @returns A reference to the elements list or undefined if it is not available (or
 *  still loading).
 */
export function useAffectedElements(
  eli: MaybeRefOrGetter<string | undefined>,
  types: MaybeRefOrGetter<ElementType[]>,
  options?: {
    amendingLawEli?: MaybeRefOrGetter<string | undefined>
  },
): DeepReadonly<Ref<Element[] | undefined>> {
  const elements = ref<Element[]>([])

  watchEffect(async () => {
    const eliVal = toValue(eli)
    const typesVal = toValue(types)
    const amendingLawEliVal = toValue(options?.amendingLawEli)

    if (!eliVal || typesVal.length === 0) return

    elements.value = await getElementsByEliAndType(eliVal, typesVal, {
      amendedBy: amendingLawEliVal,
    })
  })

  return readonly(elements)
}
