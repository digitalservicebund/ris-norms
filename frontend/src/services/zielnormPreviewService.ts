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
    return `/verkuendungen/${eliVal}/zielnormen-preview`
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
