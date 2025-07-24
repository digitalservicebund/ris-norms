import type { SimpleUseFetchReturn } from "@/services/apiService"
import { useApiFetch } from "@/services/apiService"
import type { Release } from "@/types/release"
import { ReleaseSchema } from "@/types/release"
import type { MaybeRefOrGetter } from "vue"
import { computed, toValue } from "vue"
import type { UseFetchOptions, UseFetchReturn } from "@vueuse/core"
import type { NormExpressionEli } from "@/lib/eli/NormExpressionEli"
import { z } from "zod"

function useReleaseService(
  eli: MaybeRefOrGetter<NormExpressionEli>,
  fetchOptions: UseFetchOptions = {},
): UseFetchReturn<unknown> {
  return useApiFetch(
    computed(() => `/norms/${toValue(eli)}/releases`),
    fetchOptions,
  )
}

/**
 * Get the releases of a specific verkuendung by eli.
 *
 * @param eli Eli of the norm associated with the verkuendung
 */
export function useGetReleases(
  eli: MaybeRefOrGetter<NormExpressionEli>,
): SimpleUseFetchReturn<Release[]> {
  const useFetchReturn = useReleaseService(eli, {
    refetch: true,
  })
    .json<unknown>()
    .get()

  return {
    ...useFetchReturn,
    data: computed(() =>
      z.array(ReleaseSchema).nullable().parse(useFetchReturn.data.value),
    ),
  }
}

/**
 * Release the latest release of a specific verkuendung by eli.
 *
 * @param eli Eli of the norm associated with the verkuendung
 */
export function usePostRelease(
  eli: MaybeRefOrGetter<NormExpressionEli>,
): UseFetchReturn<Release> {
  const useFetchReturn = useReleaseService(eli, {
    immediate: false,
  })
    .json()
    .post()

  return {
    ...useFetchReturn,
    data: computed(() =>
      ReleaseSchema.nullable().parse(useFetchReturn.data.value),
    ),
  }
}
