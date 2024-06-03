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

export function useNormHtmlByEli(
  eli: MaybeRefOrGetter<string | undefined>,
  at?: MaybeRefOrGetter<Date | undefined>,
): {
  normHtml: Readonly<Ref<string | undefined>>
  error: Readonly<Ref<Error | null>>
  isFetching: Readonly<Ref<boolean>>
} {
  const eliRef = ref(toValue(eli))
  const atRef = ref(toValue(at))

  watch(
    () => [toValue(eli), toValue(at)],
    () => {
      eliRef.value = toValue(eli)
      atRef.value = toValue(at)
    },
    { immediate: true },
  )

  const { data, error, isFetching } = useGetNormHtmlByEli(eliRef, false, atRef)

  const normHtml = computed(() => data.value ?? undefined)

  return {
    normHtml: readonly(normHtml),
    error: readonly(error),
    isFetching: readonly(isFetching),
  }
}
