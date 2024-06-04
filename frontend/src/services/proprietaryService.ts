import { Proprietary } from "@/types/proprietary"
import { UseFetchReturn } from "@vueuse/core"
import { MaybeRefOrGetter, computed, toValue, watchEffect } from "vue"
import { useApiFetch } from "./apiService"

/**
 * Returns the proprietary metadata of a norm from the API. Reloads when the
 * ELI changes.
 *
 * @param eli ELI of the norm
 * @returns Reactive fetch wrapper
 */
export function useGetProprietary(
  eli: MaybeRefOrGetter<string | undefined>,
): UseFetchReturn<Proprietary> {
  const url = computed(() => {
    const eliVal = toValue(eli)
    return eliVal ? `/norms/${eli}/proprietary` : ""
  })

  const proprietaryFetch = useApiFetch<Proprietary>(url, {
    immediate: false,
  }).json()

  watchEffect(() => {
    if (url.value) proprietaryFetch.execute()
  })

  return proprietaryFetch
}
