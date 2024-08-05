import { MaybeRefOrGetter, toValue, computed } from "vue"
import { INVALID_URL, useApiFetch } from "@/services/apiService"
import { UseFetchReturn } from "@vueuse/core"

/**
 * Composable for fetching references for a given norm.
 *
 * @param eli ELI of the norm
 */
export function useFetchReferences(
  eli: MaybeRefOrGetter<string | undefined>,
): UseFetchReturn<string> {
  const url = computed(() => {
    const eliVal = toValue(eli)
    if (!eliVal) return INVALID_URL
    return `/references/${eliVal}`
  })

  return useApiFetch<string>(
    url,
    {
      headers: {
        Accept: "application/xml",
        "Content-Type": "application/xml",
      },
    },
    {
      refetch: true,
    },
  ).post()
}
