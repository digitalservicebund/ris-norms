import type { DokumentExpressionEli } from "@/lib/eli/DokumentExpressionEli"
import { INVALID_URL, useApiFetch } from "@/services/apiService"
import type { Zeitgrenze } from "@/types/zeitgrenze"
import type { UseFetchReturn } from "@vueuse/core"
import type { MaybeRefOrGetter } from "vue"
import { computed, toValue } from "vue"

/**
 * Fetches the HTML content of a Norms Geltungszeiten article as HTML.
 * This function constructs a query to retrieve documents where the HTML
 * content refers specifically to the "geltungszeitregel"
 *
 * @param eli ELI of the norm
 * @returns HTML string
 */
export function useGeltungszeitenHtml(
  eli: MaybeRefOrGetter<DokumentExpressionEli | undefined>,
): UseFetchReturn<string> {
  const url = computed(
    () => `/norms/${toValue(eli)}/articles?refersTo=geltungszeitregel`,
  )

  return useApiFetch(
    url,
    { headers: { Accept: "text/html" } },
    { refetch: true },
  ).text()
}

/**
 * Fetches the Zeitgrenzen related to a Norm.
 *
 * @returns List of Zeitgrenzen
 */
export function useGetZeitgrenzen(
  eli: MaybeRefOrGetter<DokumentExpressionEli | undefined>,
): UseFetchReturn<Zeitgrenze[]> {
  const url = computed(() => {
    const eliVal = toValue(eli)
    if (!eliVal) return INVALID_URL
    return `/norms/${eliVal}/zeitgrenzen`
  })

  return useApiFetch(url, { refetch: true }).json().get()
}

/**
 * Updates the Zeitgrenzen of the Norm with the specified ELI. Will only fetch
 * when executed manually.
 *
 * @param eli ELI of the Norm
 * @param dates New Zeitgrenzen
 * @returns The updated list of Zeitgrenzen
 */
export function usePutZeitgrenzen(
  eli: MaybeRefOrGetter<DokumentExpressionEli | undefined>,
  dates: MaybeRefOrGetter<Zeitgrenze[]>,
): UseFetchReturn<Zeitgrenze[]> {
  return useApiFetch(
    computed(() => {
      const eliVal = toValue(eli)
      if (!eliVal) return INVALID_URL
      return `/norms/${eliVal}/zeitgrenzen`
    }),
    { immediate: false, refetch: false },
  )
    .json()
    .put(computed(() => JSON.stringify(toValue(dates))))
}
