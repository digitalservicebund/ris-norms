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
 * Get all articles that are changed at a specific zeitgrenze.
 * @param eli A reference to the ELI of the law for which the article data
 *  will be returned. Changing the value of the reference will load the data for the
 *  new ELI.
 * @param zeitgrenze A reference to the eid of the zeitgrenze for which these changed articles should be loaded.
 *  Changing the value of the reference will load the data for the new eid.
 * @returns A reference to the articles data or undefined if it is not available (or
 *  still loading).
 * @deprecated this is not yet correctly implemented
 */
export function useArticlesChangedAtZeitgrenze(
  eli: MaybeRefOrGetter<string>,
  zeitgrenze: MaybeRefOrGetter<string>,
): DeepReadonly<Ref<Article[] | undefined>> {
  const articles = ref<Article[]>([])

  watch(
    () => [toValue(eli), toValue(zeitgrenze)],
    async ([newEli, newZeitgrenze]) => {
      if (newZeitgrenze !== "") {
        // TODO: (Malte Laukötter, 2024-04-26) load based on zeitgrenze, and then write a unit test for it
        articles.value = await getArticlesByEli(newEli)
      }
    },
    { immediate: true },
  )

  return readonly(articles)
}
