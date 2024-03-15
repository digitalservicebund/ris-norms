import {
  getTargetLawXmlByEli,
  putTargetLawXml,
} from "@/services/targetLawsService"
import { MaybeRefOrGetter, Ref, readonly, ref, toValue, watch } from "vue"

/**
 * Get the XML of a target law.
 *
 * @param eli a reference to the eli for which the law xml will be returned.
 *  Changing the value of the reference will load the data for the new eli.
 */
export function useTargetLawXml(eli: MaybeRefOrGetter<string | undefined>): {
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
  const targetLawXml = ref<string>()

  watch(
    () => toValue(eli),
    async (eli) => {
      if (eli) {
        targetLawXml.value = await getTargetLawXmlByEli(eli)
      }
    },
    { immediate: true },
  )

  async function update(xml: string): Promise<void> {
    const eliValue = toValue(eli)

    if (!eliValue) {
      throw new Error("Expected an identifier to exist when calling update.")
    }

    targetLawXml.value = await putTargetLawXml(eliValue, xml)
  }

  return { xml: readonly(targetLawXml), update }
}
