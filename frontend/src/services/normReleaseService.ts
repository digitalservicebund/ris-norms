import { NormExpressionEli } from "@/lib/eli/NormExpressionEli"
import { NormWorkEli } from "@/lib/eli/NormWorkEli"
import type { SimpleUseFetchReturn } from "@/services/apiService"
import { INVALID_URL, useApiFetch } from "@/services/apiService"
import type { UseFetchOptions } from "@vueuse/core"
import type { MaybeRefOrGetter } from "vue"
import { computed, toValue } from "vue"
import { z } from "zod"

export type ReleaseType = "praetext" | "volldokumentation"

export const NormReleaseStatusSchema = z.object({
  normWorkEli: z.string().transform((eli) => NormWorkEli.fromString(eli)),
  title: z.string(),
  shortTitle: z.string().nullable(),
  expressions: z.array(
    z.object({
      normExpressionEli: z
        .string()
        .transform((eli) => NormExpressionEli.fromString(eli)),
      isGegenstandslos: z.boolean(),
      currentStatus: z.enum([
        "NOT_RELEASED",
        "PRAETEXT_RELEASED",
        "VOLLDOKUMENTATION_RELEASED",
      ]),
    }),
  ),
})

export type NormReleaseStatus = z.infer<typeof NormReleaseStatusSchema>

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
): SimpleUseFetchReturn<NormReleaseStatus> {
  const url = computed(() => {
    const eliVal = toValue(eli)
    if (!eliVal) return INVALID_URL
    return `/norms/${eliVal}/releases`
  })

  const useFetchReturn = useApiFetch(url, {
    refetch: true,
    ...fetchOptions,
  }).json<unknown>()

  return {
    ...useFetchReturn,
    data: computed(() =>
      NormReleaseStatusSchema.nullable().parse(useFetchReturn.data.value),
    ),
  }
}

/**
 * Posts a release request for the given norm with a releaseType
 */
export function usePostNormRelease(
  eli: MaybeRefOrGetter<NormWorkEli | undefined>,
): Omit<SimpleUseFetchReturn<NormReleaseStatus>, "execute"> & {
  execute: (releaseType: ReleaseType) => Promise<unknown>
} {
  const url = computed(() => {
    const eliVal = toValue(eli)
    if (!eliVal) return INVALID_URL
    return `/norms/${eliVal}/releases`
  })

  const useFetchReturn = useApiFetch(url, {
    immediate: false,
  }).json<unknown>()

  const execute = async (releaseType: ReleaseType) => {
    return await useFetchReturn.post({ releaseType }).execute()
  }

  return {
    ...useFetchReturn,
    data: computed(() =>
      NormReleaseStatusSchema.nullable().parse(useFetchReturn.data.value),
    ),
    execute,
  }
}
