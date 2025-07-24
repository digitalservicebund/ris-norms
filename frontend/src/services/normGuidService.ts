import type { MaybeRefOrGetter } from "vue"
import type { UseFetchOptions } from "@vueuse/core"
import type { Norm } from "@/types/norm"
import { NormSchema } from "@/types/norm"
import { computed, toValue } from "vue"
import {
  INVALID_URL,
  type SimpleUseFetchReturn,
  useApiFetch,
} from "@/services/apiService"

/**
 * Load information about a norm by its guid.
 */
export async function useNormGuidService(
  guid: MaybeRefOrGetter<string | undefined>,
  fetchOptions: UseFetchOptions = {},
): Promise<SimpleUseFetchReturn<Norm>> {
  const url = computed(() => {
    const guidVal = toValue(guid)
    if (!guidVal) return INVALID_URL

    return `/norms/${guidVal}`
  })

  const useFetchReturn = await useApiFetch(url, {
    refetch: true,
    ...fetchOptions,
  }).json<unknown>()
  return {
    ...useFetchReturn,
    data: computed(() =>
      NormSchema.nullable().parse(useFetchReturn.data.value),
    ),
  }
}
