import { useArticles } from "@/services/articleService"
import { MaybeRefOrGetter, Ref, computed } from "vue"
import { Article } from "@/types/article"
import { SimpleUseFetchReturn } from "@/services/apiService"

/**
 * Eli and zf0 eli of a norm affected by another norm
 */
type AffectedDocument = {
  /** The ELI of the target law. */
  eli: string
  /** The ELI of the first future version of the target law. */
  zf0Eli: string
}

function isArticleWithAffectedDocument(article: Article): article is Article & {
  affectedDocumentEli: string
  affectedDocumentZf0Eli: string
} {
  return (
    article.affectedDocumentEli != null &&
    article.affectedDocumentZf0Eli != null
  )
}

function mapArticleWithAffectedDocumentToAffectedDocument(
  article: Article & {
    affectedDocumentEli: string
    affectedDocumentZf0Eli: string
  },
): AffectedDocument {
  return {
    eli: article.affectedDocumentEli,
    zf0Eli: article.affectedDocumentZf0Eli,
  }
}

function removeDuplicateAffectedDocuments(
  documents: AffectedDocument[],
): AffectedDocument[] {
  return [
    ...new Map(
      documents.map(({ eli, zf0Eli }) => [eli, { eli, zf0Eli }]),
    ).values(),
  ]
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
): SimpleUseFetchReturn<AffectedDocument[]> {
  const articlesFetch = useArticles(eli)

  const affectedDocuments: Ref<AffectedDocument[]> = computed(() => {
    const affectedDocuments = (articlesFetch.data.value ?? [])
      .filter(isArticleWithAffectedDocument)
      .map((article) =>
        mapArticleWithAffectedDocumentToAffectedDocument(article),
      )

    return removeDuplicateAffectedDocuments(affectedDocuments)
  })

  return {
    ...articlesFetch,
    data: affectedDocuments,
  }
}
