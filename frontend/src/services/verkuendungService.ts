import { useApiFetch } from "@/services/apiService"
import type { UseFetchReturn } from "@vueuse/core"
import type { Norm } from "@/types/norm"
import type { Verkuendung } from "@/types/verkuendung"
import type { MaybeRefOrGetter } from "vue"
import { computed, toValue } from "vue"
import type { NormExpressionEli } from "@/lib/eli/NormExpressionEli"

/**
 * Load all verkuendungen from the API.
 */
export function useVerkuendungenService(): UseFetchReturn<Norm[]> {
  return useApiFetch("/verkuendungen").json()
}

/**
 * Fetch a specific verkuendung by its ELI.
 *
 * @param eli ELI of the verkuendung
 */
export function useGetVerkuendungService(
  eli: MaybeRefOrGetter<NormExpressionEli>,
): UseFetchReturn<Verkuendung> {
  const url = computed(() => `/verkuendungen/${toValue(eli)}`)

  return useApiFetch(url).json()
}

/**
 * Fetch the zielnormen of a specific verkuendung.
 *
 * @param eli ELI of the verkuendung
 */
export function useGetZielnormReferences(
  eli: MaybeRefOrGetter<NormExpressionEli>,
): UseFetchReturn<Norm[]> {
  const url = computed(() => `/verkuendungen/${toValue(eli)}/zielnormen`)
  return useApiFetch(url).json()
}
