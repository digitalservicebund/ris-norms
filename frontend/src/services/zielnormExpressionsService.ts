import { NormExpressionEli } from "@/lib/eli/NormExpressionEli"
import { NormWorkEli } from "@/lib/eli/NormWorkEli"
import { INVALID_URL, useApiFetch } from "@/services/apiService"
import type { ZielnormPreview } from "@/types/zielnormPreview"
import type { UseFetchOptions, UseFetchReturn } from "@vueuse/core"
import type { MaybeRefOrGetter } from "vue"
import { computed, ref, toValue, watch } from "vue"

type ZielnormPreviewExpressionResponse = {
  normExpressionEli: string
  isGegenstandslos: boolean
  isCreated: boolean
  createdBy: "diese Verkündung" | "andere Verkündung" | "System"
}

type ZielnormPreviewResponse = {
  normWorkEli: string
  title: string
  shortTitle: string
  expressions: ZielnormPreviewExpressionResponse[]
}

function mapResponseToDomain(
  response: ZielnormPreviewResponse,
): ZielnormPreview {
  const mapped: ZielnormPreview = {
    normWorkEli: NormWorkEli.fromString(response.normWorkEli),
    title: response.title,
    shortTitle: response.shortTitle,
    expressions: response.expressions.map((i) => ({
      normExpressionEli: NormExpressionEli.fromString(i.normExpressionEli),
      createdBy: i.createdBy,
      isCreated: i.isCreated,
      isGegenstandslos: i.isGegenstandslos,
    })),
  }

  return mapped
}

type UseGetZielnormPreviewReturn = Omit<
  UseFetchReturn<ZielnormPreview[]>,
  "get" | "post" | "put" | "delete" | "patch" | "head" | "options"
>

type useCreateZielnormExpressionsReturn = Omit<
  UseFetchReturn<ZielnormPreview>,
  "get" | "post" | "put" | "delete" | "patch" | "head" | "options"
>

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
): UseGetZielnormPreviewReturn {
  const url = computed(() => {
    const eliVal = toValue(eli)
    if (!eliVal) return INVALID_URL
    return `/verkuendungen/${eliVal}/zielnormen/expressions/preview`
  })

  const { data, ...rest } = useApiFetch(url, {
    refetch: true,
    ...fetchOptions,
  }).json<ZielnormPreviewResponse[]>()

  const mappedData = ref<ZielnormPreview[] | null>(null)
  watch(
    data,
    (newData) => {
      if (newData === null) mappedData.value = null
      else mappedData.value = newData.map(mapResponseToDomain)
    },
    { immediate: true },
  )

  return { ...rest, data: mappedData }
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
): useCreateZielnormExpressionsReturn {
  const url = computed(() => {
    const expressionEliVal = toValue(expressionEli)
    const zielnormWorkEliVal = toValue(zielnormWorkEli)
    if (!(expressionEliVal && zielnormWorkEliVal)) return INVALID_URL
    return `/verkuendungen/${expressionEliVal}/zielnormen/${zielnormWorkEliVal}/expressions/create`
  })

  const { data, ...rest } = useApiFetch(url, {
    ...fetchOptions,
    immediate: false,
  })
    .post()
    .json<ZielnormPreviewResponse>()

  const mappedData = ref<ZielnormPreview | null>(null)
  watch(
    data,
    (newData) => {
      if (newData === null) mappedData.value = null
      else mappedData.value = mapResponseToDomain(newData)
    },
    { immediate: true },
  )

  return { ...rest, data: mappedData }
}
