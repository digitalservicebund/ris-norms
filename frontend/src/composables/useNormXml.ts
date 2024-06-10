import { MaybeRefOrGetter, Ref, readonly, ref, toValue, watch } from "vue"
import { getNormXmlByEli, putNormXml } from "@/services/normService"

/**
 * Get the XML of a norm.
 *
 * @param eli a reference to the eli for which the norm xml will be returned.
 *  Changing the value of the reference will load the data for the new eli.
 */
export function useNormXml(eli: MaybeRefOrGetter<string | undefined>): {
  /**
   * A reference to the target law XML or undefined if it is not available (or
   * still loading).
   */
  xml: Readonly<Ref<string | undefined>>
  /**
   * Updates the target law XML.
   */
  update: (xml: string) => Promise<void>
} {
  const xml = ref<string>()

  watch(
    () => toValue(eli),
    async (eli) => {
      if (eli) {
        xml.value = await getNormXmlByEli(eli)
      }
    },
    { immediate: true },
  )

  async function update(newXml: string): Promise<void> {
    const eliValue = toValue(eli)

    if (!eliValue) {
      throw new Error("Expected an identifier to exist when calling update.")
    }

    xml.value = await putNormXml(eliValue, newXml)
  }

  return { xml: readonly(xml), update }
}
