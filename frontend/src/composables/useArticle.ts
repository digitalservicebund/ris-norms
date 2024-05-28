import { getArticleByEliAndEid } from "@/services/articleService"
import { Article } from "@/types/article"
import { LawElementIdentifier } from "@/types/lawElementIdentifier"
import {
  DeepReadonly,
  MaybeRefOrGetter,
  Ref,
  readonly,
  ref,
  toValue,
  watch,
} from "vue"

/**
 * Get the data of an article inside an amending law.
 *
 * @param identifier A reference to the ELI/eId combination for which the article data
 *  will be returned. Changing the value of the reference will load the data for the
 *  new ELI/eId combination.
 * @returns A reference to the article data or undefined if it is not available (or
 *  still loading).
 */
export function useArticle(
  identifier: MaybeRefOrGetter<LawElementIdentifier | undefined>,
): DeepReadonly<Ref<Article | undefined>> {
  const article = ref<Article>()

  watch(
    () => toValue(identifier),
    async (is, was) => {
      // Bail if only the object reference has changed, but the contents
      // are the same
      if (!is || (is.eli === was?.eli && is.eid === was?.eli)) return

      article.value = await getArticleByEliAndEid(is)
    },
    { immediate: true },
  )

  return readonly(article)
}
