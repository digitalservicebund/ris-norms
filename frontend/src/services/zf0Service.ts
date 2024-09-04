import { INVALID_URL, useApiFetch } from "@/services/apiService"
import { Norm } from "@/types/norm"
import { UseFetchReturn } from "@vueuse/core"
import { computed, MaybeRefOrGetter, toValue } from "vue"

/**
 * Returns the xml of the zf0 version of the norm from the API. Reloads when the parameters change.
 *
 * @param eli ELI of the norm
 * @param amendingNormEli ELI of the norm changing the norm
 * @returns Reactive fetch wrapper
 */
export function useZf0Service(
  eli: MaybeRefOrGetter<string | undefined>,
  amendingNormEli: MaybeRefOrGetter<string | undefined>,
): UseFetchReturn<string> & PromiseLike<UseFetchReturn<string>> {
  const url = computed(() => {
    const eliVal = toValue(eli)
    if (!eliVal) return INVALID_URL

    const queryParams = new URLSearchParams()

    const amendingNormEliVal = toValue(amendingNormEli)
    if (!amendingNormEliVal) return INVALID_URL
    queryParams.append("amendingNormEli", amendingNormEliVal)

    return `/norms/${eliVal}/zf0?${queryParams.toString()}`
  })

  return useApiFetch<Norm>(
    url,
    {},
    {
      refetch: true,
      beforeFetch(c) {
        c.options.headers = { ...c.options.headers, Accept: "application/xml" }
      },
    },
  ).text()
}
