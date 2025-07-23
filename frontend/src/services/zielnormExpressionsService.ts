import type { SimpleUseFetchReturn } from "@/services/apiService"
import { INVALID_URL, useApiFetch } from "@/services/apiService"
import type { ZielnormPreview } from "@/types/zielnormPreview"
import { ZielnormPreviewSchema } from "@/types/zielnormPreview"
import type { UseFetchOptions } from "@vueuse/core"
import type { MaybeRefOrGetter } from "vue"
import { computed, toValue } from "vue"
import { z } from "zod"
import type { NormExpressionEli } from "@/lib/eli/NormExpressionEli"
import type { NormWorkEli } from "@/lib/eli/NormWorkEli"

/**
 * Fetches the list of Zielnorm previews from the API.
 *
 * @param eli ELI of the Verkündung
 * @param [fetchOptions={}] Additional options for the fetching
 * @returns Reactive fetch wrapper for Zielnormen references
 */
export function useGetZielnormPreview(
  eli: MaybeRefOrGetter<NormExpressionEli | undefined>,
  fetchOptions: UseFetchOptions = {},
): SimpleUseFetchReturn<ZielnormPreview[]> {
  const url = computed(() => {
    const eliVal = toValue(eli)
    if (!eliVal) return INVALID_URL
    return `/verkuendungen/${eliVal}/zielnormen/expressions/preview`
  })

  const useFetchReturn = useApiFetch(url, {
    refetch: true,
    ...fetchOptions,
  }).json<unknown>()

  return {
    ...useFetchReturn,
    data: computed(() =>
      z
        .array(ZielnormPreviewSchema)
        .nullable()
        .parse(useFetchReturn.data.value),
    ),
  }
}

/**
 * Creates the new expressions of a Zielnorm for a Verkündung based on the
 * Zeitgrenzen and affected documents configured in that Verkündung.
 *
 * @param expressionEli ELI of the Verkündung
 * @param zielnormWorkEli ELI of the Zielnorm for which the new expressions
 *  should be created
 * @param [fetchOptions={}] Additional options for the fetching
 * @returns Reactive fetch wrapper for the created expressions
 */
export function useCreateZielnormExpressions(
  expressionEli: MaybeRefOrGetter<NormExpressionEli | undefined>,
  zielnormWorkEli: MaybeRefOrGetter<NormWorkEli | undefined>,
  fetchOptions: UseFetchOptions = {},
): SimpleUseFetchReturn<ZielnormPreview> {
  const url = computed(() => {
    const expressionEliVal = toValue(expressionEli)
    const zielnormWorkEliVal = toValue(zielnormWorkEli)
    if (!(expressionEliVal && zielnormWorkEliVal)) return INVALID_URL
    return `/verkuendungen/${expressionEliVal}/zielnormen/${zielnormWorkEliVal}/expressions/create`
  })

  const useFetchReturn = useApiFetch(url, {
    ...fetchOptions,
    immediate: false,
  })
    .post()
    .json<unknown>()

  return {
    ...useFetchReturn,
    data: computed(() =>
      ZielnormPreviewSchema.nullable().parse(useFetchReturn.data.value),
    ),
  }
}
