import { getArticlesByEli } from "@/services/articlesService"
import { Article } from "@/types/domain"
import { defineStore } from "pinia"
import { ref, watch } from "vue"

export const useArticlesStore = defineStore("articles", () => {
  const loadedArticles = ref<Article[]>()
  const eli = ref<string>()
  const loading = ref<boolean>(false)

  function loadArticlesByEli(newEli: string): void {
    eli.value = newEli
  }

  watch(eli, async (eli) => {
    if (eli) {
      if (loading.value) {
        // todo (Malte Laukötter, 2024-02-26): We should cancel a possibly still running call of getAmendingLawByEli here. The AbortController-API should allow us to do this.
        console.warn(
          "There is already an unfinished call to getArticleByEliAndEid, we are still creating a new one but this could have created a race condition.",
        )
      }
      loading.value = true
      loadedArticles.value = undefined
      loadedArticles.value = await getArticlesByEli(eli)
      loading.value = false
    }
  })

  return {
    loadedArticles,
    eli,
    loading,
    loadArticlesByEli,
  }
})
