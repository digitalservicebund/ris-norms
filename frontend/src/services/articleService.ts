import type { SimpleUseFetchReturn } from "@/services/apiService"
import { useApiFetch } from "@/services/apiService"
import type { Article } from "@/types/article"
import { ArticleSchema } from "@/types/article"
import type { MaybeRefOrGetter } from "vue"
import { computed, toValue } from "vue"
import type { DokumentExpressionEli } from "@/lib/eli/DokumentExpressionEli"
import { z } from "zod"

/**
 * Get the data of all articles inside a norm.
 * @param eli A reference to the ELI of the law for which the article data
 *  will be returned. Changing the value of the reference will load the data for the
 *  new ELI.
 */
export function useArticles(
  eli: MaybeRefOrGetter<DokumentExpressionEli>,
): SimpleUseFetchReturn<Article[]> {
  const useFetchReturn = useApiFetch<unknown>(
    computed(() => {
      return `/norms/${toValue(eli)}/articles`
    }),
    {
      refetch: true,
    },
  )
    .json<unknown>()
    .get()

  return {
    ...useFetchReturn,
    data: computed(() =>
      z.array(ArticleSchema).nullable().parse(useFetchReturn.data.value),
    ),
  }
}
