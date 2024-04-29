import { getNormXmlByEli, putNormXml } from "@/services/normService"
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
   * Update the article xml.
   */
  update: (xml: string) => Promise<unknown>
} {
  const articleXml = ref<string>()

  watch(
    () => toValue(identifier),
    async (is, was) => {
      // Bail if only the object reference has changed, but the contents
      // are the same
      if (!is || (is.eli === was?.eli && is.eid === was?.eid)) return

      // TODO: Switch this to article XML once we have that. For now we'll display
      // the entire amending law.
      articleXml.value = await getNormXmlByEli(is.eli)
    },
    { immediate: true },
  )

  async function update(xml: string) {
    const id = toValue(identifier)

    if (!id) {
      throw new Error("Expected an identifier to exist when calling update.")
    }

    articleXml.value = await putNormXml(id.eli, xml)
  }

  return {
    xml: readonly(articleXml),
    update,
  }
}
