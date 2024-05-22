import { getElementByEliAndEid } from "@/services/elementsService"
import { Element } from "@/types/element"
import { LawElementIdentifier } from "@/types/lawElementIdentifier"
import {
  DeepReadonly,
  MaybeRefOrGetter,
  Ref,
  readonly,
  ref,
  toValue,
  watch,
} from "vue"

/**
 * Get an element inside an amending law.
 *
 * @param eli A reference to the ELI of the norm for which the element will
 *  be returned.
 * @param eid A reference to the eId of the element for which the element
 *  will be returned.
 * @returns A reference to the element or undefined if it is not available
 *  (or still loading).
 */
export function useElement(
  identifier: MaybeRefOrGetter<LawElementIdentifier | undefined>,
): DeepReadonly<Ref<Element | undefined>> {
  const element = ref<Element | undefined>()

  watch(
    () => toValue(identifier),
    async (is, was) => {
      if (
        is?.eid &&
        is?.eli &&
        // Check if any of the old properties have changed
        !(is.eid === was?.eid && is.eli === was?.eli)
      ) {
        element.value = await getElementByEliAndEid(is.eli, is.eid)
      }
    },
    { immediate: true },
  )

  return readonly(element)
}
