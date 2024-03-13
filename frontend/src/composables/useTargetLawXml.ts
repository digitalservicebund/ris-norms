import {
  readonly,
  watch,
  MaybeRefOrGetter,
  toValue,
  Ref,
  DeepReadonly,
  ref,
} from "vue"
import { getTargetLawXmlByEli } from "@/services/targetLawsService"

/**
 * Get the xml of a target law.
 * @param eli a reference to the eli for which the law xml will be returned. Changing the value of the reference will load the data for the new eli.
 * @returns A reference to the target law xml or undefined if it is not available (or still loading).
 */
export function useTargetLawXml(
  eli: MaybeRefOrGetter<string | undefined>,
): DeepReadonly<Ref<string | undefined>> {
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

  return readonly(targetLawXml)
}
