import { getAmendingLawXmlByEli } from "@/services/amendingLawsService"
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
 * Get the XML of an article inside an amending law.
 *
 * @param identifier A reference to the ELI/eId combination for which the article XML
 *  will be returned. Changing the value of the reference will load the XML for the
 *  new ELI/eId combination.
 */
export function useArticleXml(
  identifier: MaybeRefOrGetter<LawElementIdentifier | undefined>,
): {
  /**
   * A reference to the article XML or undefined if it is not available (or
   *  still loading).
   */
  xml: DeepReadonly<Ref<string | undefined>>
  /**
   * A function to call so the xml is refreshed. It is not necessary to call this when the identifier changed.
   */
  refresh: () => Promise<unknown>
} {
  const articleXml = ref<string>()

  async function load(identifier: LawElementIdentifier) {
    // TODO: Switch this to article XML once we have that. For now we'll display
    // the entire amending law.
    articleXml.value = await getAmendingLawXmlByEli(identifier.eli)
  }

  watch(
    () => toValue(identifier),
    async (is, was) => {
      // Bail if only the object reference has changed, but the contents
      // are the same
      if (!is || (is.eli === was?.eli && is.eid === was?.eid)) return

      await load(is)
    },
    { immediate: true },
  )

  async function refresh() {
    const id = toValue(identifier)
    if (id) {
      await load(id)
    }
  }

  return {
    xml: readonly(articleXml),
    refresh,
  }
}
