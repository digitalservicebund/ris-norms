import { useArticlesStore } from "@/store/articlesStore"
import { Article } from "@/types/domain"
import { storeToRefs } from "pinia"
import {
  DeepReadonly,
  MaybeRefOrGetter,
  Ref,
  readonly,
  toValue,
  watch,
} from "vue"

/**
 * Get the data of all articles inside an amending law.
 * @param identifier A reference to the ELI of the law for which the article data
 *  will be returned. Changing the value of the reference will load the data for the
 *  new ELI.
 * @returns A reference to the articles data or undefined if it is not available (or
 *  still loading).
 */
export function useArticles(
  eli: MaybeRefOrGetter<string>,
): DeepReadonly<Ref<Article[] | undefined>> {
  const articlesStore = useArticlesStore()
  const { loadedArticles } = storeToRefs(articlesStore)

  watch(
    () => toValue(eli),
    (newEli) => {
      articlesStore.loadArticlesByEli(newEli)
    },
    { immediate: true },
  )

  return readonly(loadedArticles)
}
