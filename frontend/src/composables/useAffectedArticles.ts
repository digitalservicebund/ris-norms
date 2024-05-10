import { getArticlesByEli } from "@/services/articlesService"
import { Article } from "@/types/article"
import {
  DeepReadonly,
  MaybeRefOrGetter,
  Ref,
  readonly,
  ref,
  toValue,
  watchEffect,
} from "vue"

/**
 * Get all articles in a law that have pending changes (aka. passive modifications).
 * Articles can be filtered by a time boundary on which the change takes effect, the
 * law making the change, or both. If you don't need any filtering related to pending
 * changes, use `useArticles` instead.
 *
 * @param eli A reference to the ELI of the law for which the article data
 *  will be returned. Changing the value of the reference will load the data for the
 *  new ELI.
 * @param timeBoundaryEid A reference to the eId of the time boundary for which the
 *  changed articles should be loaded. Changing the value of the reference will load
 *  the data for the new eId.
 * @param amendingLawEli A reference to the ELI of the amending law for which the
 *  changed articles should be loaded. Changing the value of the reference will load
 *  the data for the new eId.
 * @returns A reference to the articles list or undefined if it is not available (or
 *  still loading).
 */
export function useAffectedArticles(
  eli: MaybeRefOrGetter<string | undefined>,
  timeBoundaryEid: MaybeRefOrGetter<string | undefined>,
  amendingLawEli: MaybeRefOrGetter<string | undefined>,
): DeepReadonly<Ref<Article[] | undefined>> {
  const articles = ref<Article[]>([])

  watchEffect(async () => {
    const eliVal = toValue(eli)
    const timeBoundaryEidVal = toValue(timeBoundaryEid)
    const amendingLawEliVal = toValue(amendingLawEli)

    // We require the ELI and either the time boundary or the amending law
    if (!eliVal || !(timeBoundaryEidVal || amendingLawEliVal)) return

    articles.value = await getArticlesByEli(eliVal, {
      amendedAt: timeBoundaryEidVal,
      amendedBy: amendingLawEliVal,
    })
  })

  return readonly(articles)
}
