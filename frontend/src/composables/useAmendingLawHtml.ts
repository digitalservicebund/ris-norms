import { MaybeRefOrGetter, Ref, readonly, ref, toValue, watch } from "vue"
import { getAmendingLawHtmlByEli } from "@/services/amendingLawsService"

/**
 * Get the rendered HTML of an amending law.
 *
 * @param eli a reference to the eli for which the law xml will be returned.
 *  Changing the value of the reference will load the data for the new eli.
 *
 * @returns A reference to the amending law rendered HTML or undefined if it is not available (or still loading).
 */
export function useAmendingLawHtml(eli: MaybeRefOrGetter<string | undefined>): {
  html: Readonly<Ref<string | undefined>>

  reload: () => Promise<void>
} {
  const amendingLawHtml = ref<string>()

  watch(
    () => toValue(eli),
    async (eli) => {
      if (eli) {
        amendingLawHtml.value = await getAmendingLawHtmlByEli(eli)
      }
    },
    { immediate: true },
  )

  async function reload() {
    const eliVal = toValue(eli)
    if (eliVal) amendingLawHtml.value = await getAmendingLawHtmlByEli(eliVal)
  }

  return {
    html: readonly(amendingLawHtml),
    reload,
  }
}
