import { MaybeRefOrGetter, Ref, readonly, ref, toValue, watch } from "vue"
import { getNormHtmlByEli } from "@/services/normService"

/**
 * Get the rendered HTML of an amending law.
 *
 * @param eli a reference to the eli for which the law xml will be returned.
 *  Changing the value of the reference will load the data for the new eli.
 *
 * @returns A reference to the amending law rendered HTML or undefined if it is not available (or still loading).
 */
export function useAmendingLawHtml(
  eli: MaybeRefOrGetter<string | undefined>,
): Readonly<Ref<string | undefined>> {
  const amendingLawHtml = ref<string>()

  watch(
    () => toValue(eli),
    async (eli) => {
      if (eli) {
        amendingLawHtml.value = await getNormHtmlByEli(eli)
      }
    },
    { immediate: true },
  )

  return readonly(amendingLawHtml)
}
