import { useArticles } from "@/services/articleService"
import { Norm } from "@/types/norm"
import { DeepReadonly, MaybeRefOrGetter, Ref, readonly, ref, watch } from "vue"
import { Article } from "@/types/article"
import { getNormByEli } from "@/services/normService"

export type TargetLawWithZF0Eli = {
  /** The ELI of the target law. */
  targetLaw: Norm

  /** The ELI of the first future version of the target law. */
  targetLawZf0Eli: string
}

function isArticleWithAffectedDocument(article: Article): article is Article & {
  affectedDocumentEli: string
  affectedDocumentZf0Eli: string
} {
  return (
    article.affectedDocumentEli !== null &&
    article.affectedDocumentZf0Eli !== null
  )
}

/**
 * Get the data of all target laws changed by the amending law with the
 * specified ELI. In the context of the amending law, those target laws
 * are called "affected documents".
 *
 * @param eli The ELI of the amending law whose affected documents we want
 *  to fetch.
 * @returns A reference to the affected documents (or empty array if there
 *  are none or the documents are still loading)
 */
export function useAffectedDocuments(
  eli: MaybeRefOrGetter<string>,
): DeepReadonly<Ref<TargetLawWithZF0Eli[]>> {
  const targetLaws = ref<TargetLawWithZF0Eli[]>([])

  const { data: articles } = useArticles(eli)

  watch(
    articles,
    async (value, oldValue, onCleanup) => {
      const abortController = new AbortController()
      onCleanup(() => {
        abortController.abort()
      })

      targetLaws.value = await Promise.all(
        (articles.value ?? [])
          .filter(isArticleWithAffectedDocument)
          .map(async (article) => ({
            targetLaw: await getNormByEli(article.affectedDocumentEli, {
              signal: abortController.signal,
            }),
            targetLawZf0Eli: article.affectedDocumentZf0Eli,
          })),
      )
    },
    { immediate: true, deep: true },
  )

  return readonly(targetLaws)
}
