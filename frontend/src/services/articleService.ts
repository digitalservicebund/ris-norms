import { useApiFetch } from "@/services/apiService"
import type { Article } from "@/types/article"
import type { MaybeRefOrGetter } from "vue"
import { computed, toValue } from "vue"
import type { UseFetchReturn } from "@vueuse/core"
import type { DokumentExpressionEli } from "@/lib/eli/DokumentExpressionEli"

/**
 * Get the data of all articles inside a norm.
 * @param eli A reference to the ELI of the law for which the article data
 *  will be returned. Changing the value of the reference will load the data for the
 *  new ELI.
 */
export function useArticles(
  eli: MaybeRefOrGetter<DokumentExpressionEli>,
): UseFetchReturn<Article[]> {
  return useApiFetch(
    computed(() => {
      return `/norms/${toValue(eli)}/articles`
    }),
    {
      refetch: true,
    },
  )
    .json()
    .get()
}
