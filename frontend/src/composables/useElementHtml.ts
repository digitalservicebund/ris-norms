import { getElementHtmlByEliAndEid } from "@/services/elementsService"
import { LawElementIdentifier } from "@/types/lawElementIdentifier"
import {
  DeepReadonly,
  MaybeRefOrGetter,
  readonly,
  Ref,
  ref,
  toValue,
  watch,
} from "vue"

/**
 * Get the rendered html of an article inside an amending law.
 *
 * @param eli A reference to the ELI of the norm for which the article XML will be returned.
 * @param eid A reference to the eId of the article for which the article XML will be returned.
 * @param at Date indicating which modifications should be applied before the HTML gets rendered and returned
 * @returns A reference to the article HTML or undefined if it is not available (or
 *  still loading).
 */
export function useElementHtml(
  identifier: MaybeRefOrGetter<LawElementIdentifier>,
): DeepReadonly<Ref<string | undefined>> {
  const html = ref<string>()

  watch(
    () => toValue(identifier),
    async ({ eli, eid }, oldVal) => {
      if (!eli || !eid || (eli === oldVal?.eli && eid === oldVal?.eid)) return

      html.value = await getElementHtmlByEliAndEid(eli, eid)
    },
    { immediate: true },
  )

  return readonly(html)
}
