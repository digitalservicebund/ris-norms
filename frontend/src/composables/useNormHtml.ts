import { MaybeRefOrGetter, Ref, readonly, ref, toValue, watch } from "vue"
import { getNormHtmlByEli } from "@/services/normService"

/**
 * Get the rendered HTML of a norm.
 *
 * @param eli a reference to the eli for which the norm html render will be returned.
 *  Changing the value of the reference will load the data for the new eli.
 * @param at Date indicating which modifications should be applied before the HTML gets rendered and returned
 * @returns A reference to the norm rendered as HTML or undefined if it is not available (or still loading).
 */
export function useNormHtml(
  eli: MaybeRefOrGetter<string | undefined>,
  at?: MaybeRefOrGetter<Date | undefined>,
): Readonly<Ref<string | undefined>> {
  const normHtml = ref<string>()

  watch(
    () => [toValue(eli), toValue(at)],
    async () => {
      const eliValue = toValue(eli)
      if (eliValue) {
        normHtml.value = await getNormHtmlByEli(eliValue, false, toValue(at))
      }
    },
    { immediate: true },
  )

  return readonly(normHtml)
}
