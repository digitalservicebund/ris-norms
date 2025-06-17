import { NormExpressionEli } from "@/lib/eli/NormExpressionEli"
import { NormWorkEli } from "@/lib/eli/NormWorkEli"
import { INVALID_URL, useApiFetch } from "@/services/apiService"
import type { UseFetchOptions, UseFetchReturn } from "@vueuse/core"
import type { MaybeRefOrGetter } from "vue"
import { computed, ref, toValue, watch } from "vue"

export type ReleaseType = "praetext" | "volldokumentation"

type ZielnormReleaseStatus = {
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
export type ZielnormReleaseStatusDomain = {
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
  response: ZielnormReleaseStatus,
): ZielnormReleaseStatusDomain {
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

type UseGetZielnormReleaseStatusReturn = Omit<
  UseFetchReturn<ZielnormReleaseStatusDomain | null>,
  "get" | "post" | "put" | "delete" | "patch" | "head" | "options"
>

/**
 * Fetches the release statuses of expressions belonging to a Zielnorm.
 *
 * @param eli Work ELI of the Zielnorm
 * @param [fetchOptions={}] Additional options for the fetching
 * * @returns Reactive fetch wrapper for Zielnorm release status
 */
export function useGetZielnormReleaseStatus(
  eli: MaybeRefOrGetter<NormWorkEli | undefined>,
  fetchOptions: UseFetchOptions = {},
): UseGetZielnormReleaseStatusReturn {
  const url = computed(() => {
    const eliVal = toValue(eli)
    if (!eliVal) return INVALID_URL
    return `/${eliVal}/releases`
  })

  const { data, ...rest } = useApiFetch(url, {
    refetch: true,
    ...fetchOptions,
  }).json<ZielnormReleaseStatus>()

  const mappedData = ref<ZielnormReleaseStatusDomain | null>(null)
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
 * Posts a release request for the given Zielnorm with a releaseType
 */
export function usePostZielnormRelease(
  eli: MaybeRefOrGetter<NormWorkEli | undefined>,
) {
  const url = computed(() => {
    const eliVal = toValue(eli)
    if (!eliVal) return INVALID_URL
    return `/${eliVal}/releases`
  })

  const {
    data: rawData,
    post,
    ...rest
  } = useApiFetch(url, {
    immediate: false,
  }).json<ZielnormReleaseStatus>()

  const mappedData = ref<ZielnormReleaseStatusDomain | null>(null)

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
    console.log(releaseType)
    return await post({ releaseType }).execute()
  }

  return {
    ...rest,
    data: mappedData,
    execute,
  }
}
