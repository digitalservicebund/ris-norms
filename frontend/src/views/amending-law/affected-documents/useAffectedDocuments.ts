import { useArticles } from "@/services/articleService"
import { MaybeRefOrGetter, Ref, computed } from "vue"
import { Article } from "@/types/article"
import { SimpleUseFetchReturn } from "@/services/apiService"

function isArticleWithAffectedDocument(article: Article): article is Article & {
  affectedDocumentEli: string
} {
  return article.affectedDocumentEli != null
}

function removeDuplicateAffectedDocuments(documents: string[]): string[] {
  return [...new Set(documents).values()]
}

/**
 * Get the elis (and zf0elis) of all target laws changed by the amending law with the
 * specified ELI. In the context of the amending law, those target laws
 * are called "affected documents".
 *
 * @param eli The ELI of the amending law whose affected documents we want
 *  to fetch.
 * @returns A UseFetchReturn for the affected documents
 */
export function useAffectedDocuments(
  eli: MaybeRefOrGetter<string>,
): SimpleUseFetchReturn<string[]> {
  const articlesFetch = useArticles(eli)

  const affectedDocuments: Ref<string[]> = computed(() => {
    const affectedDocuments = (articlesFetch.data.value ?? [])
      .filter(isArticleWithAffectedDocument)
      .map((article) => article.affectedDocumentEli)

    return removeDuplicateAffectedDocuments(affectedDocuments)
  })

  return {
    ...articlesFetch,
    data: affectedDocuments,
  }
}
