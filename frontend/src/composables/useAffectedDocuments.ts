import { useArticles } from "@/composables/useArticles"
import { getTargetLawByEli } from "@/services/targetLawsService"
import { TargetLaw } from "@/types/targetLaw"
import { DeepReadonly, MaybeRefOrGetter, Ref, readonly, ref, watch } from "vue"
import { Article } from "@/types/article"

export type TargetLawWithZF0Eli = {
  targetLaw: TargetLaw
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

  const articles = useArticles(eli)

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
            targetLaw: await getTargetLawByEli(article.affectedDocumentEli, {
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
