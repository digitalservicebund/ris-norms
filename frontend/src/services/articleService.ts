import { useApiFetch } from "@/services/apiService"
import { Article } from "@/types/article"
import { computed, MaybeRefOrGetter, toValue } from "vue"
import { UseFetchReturn } from "@vueuse/core"

/**
 * Get the data of all articles inside a norm.
 * @param eli A reference to the ELI of the law for which the article data
 *  will be returned. Changing the value of the reference will load the data for the
 *  new ELI.
 */
export function useArticles(
  eli: MaybeRefOrGetter<string>,
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
