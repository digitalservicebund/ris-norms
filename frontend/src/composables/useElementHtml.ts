import { getElementHtmlByEliAndEid } from "@/services/elementsService"
import { LawElementIdentifier } from "@/types/lawElementIdentifier"
import {
  DeepReadonly,
  MaybeRefOrGetter,
  readonly,
  Ref,
  ref,
  toValue,
  watchEffect,
} from "vue"

/**
 * Get the rendered HTML of an element inside an amending law.
 *
 * @param eli A reference to the ELI of the norm for which the element XML will
 *  be returned.
 * @param eid A reference to the eId of the element for which the element XML
 *  will be returned.
 * @param options Optional additional filters and queries.
 * @returns A reference to the element HTML or undefined if it is not available
 *  (or still loading).
 */
export function useElementHtml(
  identifier: MaybeRefOrGetter<LawElementIdentifier | undefined>,
  options?: {
    /** If set, applies all modifications until and including that date. */
    at?: MaybeRefOrGetter<Date | undefined>
  },
): DeepReadonly<Ref<string | undefined>> {
  const html = ref<string>()
  let oldId: LawElementIdentifier | undefined = undefined
  let oldDate: Date | undefined = undefined

  watchEffect(async () => {
    const idVal = toValue(identifier)
    const dateVal = toValue(options?.at)

    if (
      idVal?.eid &&
      idVal?.eli &&
      // Check if any of the old properties have changed
      !(
        idVal.eid === oldId?.eid &&
        idVal.eli === oldId?.eli &&
        dateVal?.toISOString() === oldDate?.toISOString()
      )
    ) {
      html.value = await getElementHtmlByEliAndEid(idVal.eli, idVal.eid, {
        at: dateVal,
      })
    }

    oldId = idVal
    oldDate = dateVal
  })

  return readonly(html)
}
