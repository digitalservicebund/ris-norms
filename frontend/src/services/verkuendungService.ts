import type { SimpleUseFetchReturn } from "@/services/apiService"
import { useApiFetch } from "@/services/apiService"
import type { Norm } from "@/types/norm"
import { NormSchema } from "@/types/norm"
import type { Verkuendung } from "@/types/verkuendung"
import { VerkuendungSchema } from "@/types/verkuendung"
import type { MaybeRefOrGetter } from "vue"
import { computed, toValue } from "vue"
import type { NormExpressionEli } from "@/lib/eli/NormExpressionEli"
import { z } from "zod"

/**
 * Load all verkuendungen from the API.
 */
export function useVerkuendungenService(): SimpleUseFetchReturn<Norm[]> {
  const useFetchReturn = useApiFetch("/verkuendungen").json<unknown>()
  return {
    ...useFetchReturn,
    data: computed(() =>
      z.array(NormSchema).nullable().parse(useFetchReturn.data.value),
    ),
  }
}

/**
 * Fetch a specific verkuendung by its ELI.
 *
 * @param eli ELI of the verkuendung
 */
export function useGetVerkuendungService(
  eli: MaybeRefOrGetter<NormExpressionEli>,
): SimpleUseFetchReturn<Verkuendung> {
  const url = computed(() => `/verkuendungen/${toValue(eli)}`)

  const useFetchReturn = useApiFetch(url, { refetch: true }).json<unknown>()
  return {
    ...useFetchReturn,
    data: computed(() =>
      VerkuendungSchema.nullable().parse(useFetchReturn.data.value),
    ),
  }
}

/**
 * Fetch the zielnormen of a specific verkuendung.
 *
 * @param eli ELI of the verkuendung
 */
export function useGetZielnormReferences(
  eli: MaybeRefOrGetter<NormExpressionEli>,
): SimpleUseFetchReturn<Norm[]> {
  const url = computed(() => `/verkuendungen/${toValue(eli)}/zielnormen`)
  const useFetchReturn = useApiFetch(url, { refetch: true }).json<unknown>()
  return {
    ...useFetchReturn,
    data: computed(() =>
      z.array(NormSchema).nullable().parse(useFetchReturn.data.value),
    ),
  }
}
