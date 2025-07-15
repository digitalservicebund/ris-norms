import { NormExpressionEli } from "@/lib/eli/NormExpressionEli"
import { NormWorkEli } from "@/lib/eli/NormWorkEli"
import { INVALID_URL, useApiFetch } from "@/services/apiService"
import type { UseFetchOptions, UseFetchReturn } from "@vueuse/core"
import type { MaybeRefOrGetter } from "vue"
import { computed, ref, toValue, watch } from "vue"

export type ReleaseType = "praetext" | "volldokumentation"

type NormReleaseStatus = {
  normWorkEli: string
  title: string
  shortTitle: string
  expressions: {
    normExpressionEli: string
    isGegenstandslos: boolean
    currentStatus:
      | "NOT_RELEASED"
      | "PRAETEXT_RELEASED"
      | "VOLLDOKUMENTATION_RELEASED"
  }[]
}
export type NormReleaseStatusDomain = {
  normWorkEli: NormWorkEli
  title: string
  shortTitle: string
  expressions: {
    normExpressionEli: NormExpressionEli
    isGegenstandslos: boolean
    currentStatus:
      | "NOT_RELEASED"
      | "PRAETEXT_RELEASED"
      | "VOLLDOKUMENTATION_RELEASED"
  }[]
}

export function mapReleaseStatusResponseToDomain(
  response: NormReleaseStatus,
): NormReleaseStatusDomain {
  return {
    normWorkEli: NormWorkEli.fromString(response.normWorkEli),
    title: response.title,
    shortTitle: response.shortTitle,
    expressions: response.expressions.map((expr) => ({
      normExpressionEli: NormExpressionEli.fromString(expr.normExpressionEli),
      isGegenstandslos: expr.isGegenstandslos,
      currentStatus: expr.currentStatus,
    })),
  }
}

type UseGetNormReleaseStatusReturn = Omit<
  UseFetchReturn<NormReleaseStatusDomain | null>,
  "get" | "post" | "put" | "delete" | "patch" | "head" | "options"
>

/**
 * Fetches the release statuses of expressions belonging to a norm.
 *
 * @param eli Work ELI of the norm
 * @param [fetchOptions={}] Additional options for the fetching
 * * @returns Reactive fetch wrapper for norm release status
 */
export function useGetNormReleaseStatus(
  eli: MaybeRefOrGetter<NormWorkEli | undefined>,
  fetchOptions: UseFetchOptions = {},
): UseGetNormReleaseStatusReturn {
  const url = computed(() => {
    const eliVal = toValue(eli)
    if (!eliVal) return INVALID_URL
    return `/norms/${eliVal}/releases`
  })

  const { data, ...rest } = useApiFetch(url, {
    refetch: true,
    ...fetchOptions,
  }).json<NormReleaseStatus>()

  const mappedData = ref<NormReleaseStatusDomain | null>(null)
  watch(
    data,
    (newData) => {
      if (newData === null) mappedData.value = null
      else mappedData.value = mapReleaseStatusResponseToDomain(newData)
    },
    { immediate: true },
  )

  return { ...rest, data: mappedData }
}

/**
 * Posts a release request for the given norm with a releaseType
 */
export function usePostNormRelease(
  eli: MaybeRefOrGetter<NormWorkEli | undefined>,
) {
  const url = computed(() => {
    const eliVal = toValue(eli)
    if (!eliVal) return INVALID_URL
    return `/norms/${eliVal}/releases`
  })

  const {
    data: rawData,
    post,
    ...rest
  } = useApiFetch(url, {
    immediate: false,
  }).json<NormReleaseStatus>()

  const mappedData = ref<NormReleaseStatusDomain | null>(null)

  watch(
    rawData,
    (newData) => {
      mappedData.value = newData
        ? mapReleaseStatusResponseToDomain(newData)
        : null
    },
    { immediate: true },
  )

  const execute = async (releaseType: ReleaseType) => {
    return await post({ releaseType }).execute()
  }

  return {
    ...rest,
    data: mappedData,
    execute,
  }
}
