import { MaybeRefOrGetter, Ref, readonly, ref, toValue, watch } from "vue"
import { getNormHtmlByEli } from "@/services/normService"

/**
 * Get the rendered HTML of a norm.
 *
 * @param eli a reference to the eli for which the norm html render will be returned.
 *  Changing the value of the reference will load the data for the new eli.
 *
 * @returns A reference to the norm rendered as HTML or undefined if it is not available (or still loading).
 */
export function useNormHtml(
  eli: MaybeRefOrGetter<string | undefined>,
): Readonly<Ref<string | undefined>> {
  const normHtml = ref<string>()

  watch(
    () => toValue(eli),
    async (eli) => {
      if (eli) {
        normHtml.value = await getNormHtmlByEli(eli)
      }
    },
    { immediate: true },
  )

  return readonly(normHtml)
}
