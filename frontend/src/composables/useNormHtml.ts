import {
  MaybeRefOrGetter,
  Ref,
  readonly,
  ref,
  toValue,
  watch,
  computed,
} from "vue"
import { getNormHtmlByEli, useGetNormHtmlByEli } from "@/services/normService"

/**
 * Get the rendered HTML of a norm.
 *
 * @deprecated This function is deprecated and should not be used in new code.
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

/**
 * Get the rendered HTML of a norm.
 *
 * @param eli a reference to the eli for which the norm html render will be returned.
 *  Changing the value of the reference will load the data for the new eli.
 * @param at Date indicating which modifications should be applied before the HTML gets rendered and returned
 * @returns A reference to the norm rendered as HTML or undefined if it is not available (or still loading).
 */
export function useNormHtmlByEli(
  eli: MaybeRefOrGetter<string | undefined>,
  at?: MaybeRefOrGetter<Date | undefined>,
): {
  normHtml: Readonly<Ref<string | undefined>>
  error: Readonly<Ref<Error | null>>
  isFetching: Readonly<Ref<boolean>>
} {
  const { data, error, isFetching } = useGetNormHtmlByEli(
    toValue(eli),
    false,
    toValue(at),
  )

  const normHtml = computed(() => data.value ?? undefined)

  return {
    normHtml: readonly(normHtml),
    error: readonly(error),
    isFetching: readonly(isFetching),
  }
}
