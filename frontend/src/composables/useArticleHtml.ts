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
import { getArticleRenderByEliAndEid } from "@/services/articlesService"

/**
 * Get the rendered html of an article inside an amending law.
 *
 * @param identifier A reference to the ELI/eId combination for which the article XML
 *  will be returned. Changing the value of the reference will load the XML for the
 *  new ELI/eId combination.
 * @returns A reference to the article HTML or undefined if it is not available (or
 *  still loading).
 */
export function useArticleHtml(
  identifier: MaybeRefOrGetter<LawElementIdentifier | undefined>,
): DeepReadonly<Ref<string | undefined>> {
  const html = ref<string>()

  watch(
    () => toValue(identifier),
    async (is, was) => {
      // Bail if only the object reference has changed, but the contents
      // are the same
      if (!is || (is.eli === was?.eli && is.eid === was?.eid)) return

      html.value = await getArticleRenderByEliAndEid(is)
    },
    { immediate: true },
  )

  return readonly(html)
}
