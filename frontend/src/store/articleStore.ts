import { getArticleByEliAndEid } from "@/services/articlesService"
import { Article } from "@/types/domain"
import { LawElementIdentifier } from "@/types/lawElementIdentifier"
import { defineStore } from "pinia"
import { ref, watch } from "vue"

export const useArticleStore = defineStore("article", () => {
  const loadedArticle = ref<Article>()
  const identifier = ref<LawElementIdentifier>()
  const loading = ref<boolean>(false)

  function loadArticleByEliAndEid(newIdentifier: LawElementIdentifier): void {
    identifier.value = newIdentifier
  }

  watch(identifier, async (identifier) => {
    if (identifier) {
      if (loading.value) {
        // todo (Malte Laukötter, 2024-02-26): We should cancel a possibly still running call of getAmendingLawByEli here. The AbortController-API should allow us to do this.
        console.warn(
          "There is already an unfinished call to getArticleByEliAndEid, we are still creating a new one but this could have created a race condition.",
        )
      }
      loading.value = true
      loadedArticle.value = undefined
      loadedArticle.value = await getArticleByEliAndEid(identifier)
      loading.value = false
    }
  })

  return {
    loadedArticle,
    identifier,
    loading,
    loadArticleByEliAndEid,
  }
})
