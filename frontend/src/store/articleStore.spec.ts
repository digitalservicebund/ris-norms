import { getArticleByEliAndEid } from "@/services/articlesService"
import { AmendingLawArticle } from "@/types/domain"
import { createPinia, setActivePinia } from "pinia"
import { beforeEach, describe, expect, it, vi } from "vitest"
import { nextTick } from "vue"
import { useArticleStore } from "./articleStore"

vi.mock("@/services/articlesService", () => ({
  getArticleByEliAndEid: vi.fn(),
}))

describe("useArticlesStore", () => {
  let mockArticle: AmendingLawArticle

  beforeEach(() => {
    setActivePinia(createPinia())

    mockArticle = {
      eid: "article eid 1",
      title: "article eid 1",
      enumeration: "1",
      affectedDocumentEli: "affected document eli 1",
    }

    vi.mocked(getArticleByEliAndEid).mockResolvedValue(mockArticle)
  })

  it("loads articles correctly", async () => {
    const store = useArticleStore()

    store.loadArticleByEliAndEid({ eli: "example/eli", eid: "article eid 1" })
    await nextTick()

    expect(store.loading).toBe(true)
    expect(getArticleByEliAndEid).toHaveBeenCalledOnce()

    await vi.waitUntil(() => !store.loading)

    expect(store.loading).toBe(false)
    expect(store.loadedArticle).toEqual(mockArticle)
  })
})
