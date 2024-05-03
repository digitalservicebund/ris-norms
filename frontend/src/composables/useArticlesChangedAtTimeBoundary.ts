import { Article } from "@/types/article"
import {
  DeepReadonly,
  MaybeRefOrGetter,
  Ref,
  readonly,
  toValue,
  watch,
  ref,
} from "vue"
import { getArticlesByEli } from "@/services/articlesService"

/**
 * Get all articles that are changed at a specific time boundary.
 * @param eli A reference to the ELI of the law for which the article data
 *  will be returned. Changing the value of the reference will load the data for the
 *  new ELI.
 * @param timeBoundary A reference to the eid of the time boundary for which these changed articles should be loaded.
 *  Changing the value of the reference will load the data for the new eid.
 * @returns A reference to the articles data or undefined if it is not available (or
 *  still loading).
 */
export function useArticlesChangedAtTimeBoundary(
  eli: MaybeRefOrGetter<string | undefined>,
  timeBoundaryEid: MaybeRefOrGetter<string | undefined>,
  amendingLawEli: MaybeRefOrGetter<string | undefined>,
): DeepReadonly<Ref<Article[] | undefined>> {
  const articles = ref<Article[]>([])

  watch(
    () => [toValue(eli), toValue(timeBoundaryEid), toValue(amendingLawEli)],
    async ([newEli, newTimeBoundary, newAmendingLawEli]) => {
      if (newEli && newTimeBoundary && newAmendingLawEli) {
        articles.value = await getArticlesByEli(newEli, {
          amendedAt: newTimeBoundary,
          amendedBy: newAmendingLawEli,
        })
      }
    },
    { immediate: true },
  )

  return readonly(articles)
}
