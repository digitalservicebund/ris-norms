import { getAmendingLawXmlByEli } from "@/services/amendingLawsService"
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
 * Get the XML of an article inside an amending law.
 *
 * @param identifier A reference to the ELI/eId combination for which the article XML
 *  will be returned. Changing the value of the reference will load the XML for the
 *  new ELI/eId combination.
 * @returns A reference to the article XML or undefined if it is not available (or
 *  still loading).
 */
export function useArticleXml(
  identifier: MaybeRefOrGetter<LawElementIdentifier | undefined>,
): DeepReadonly<Ref<string | undefined>> {
  const articleXml = ref<string>()

  watch(
    () => toValue(identifier),
    async (is, was) => {
      // Bail if only the object reference has changed, but the contents
      // are the same
      if (!is || (is.eli === was?.eli && is.eid === was?.eid)) return

      // TODO: Switch this to article XML once we have that. For now we'll display
      // the entire amending law.
      articleXml.value = await getAmendingLawXmlByEli(is.eli)
    },
    { immediate: true },
  )

  return readonly(articleXml)
}
