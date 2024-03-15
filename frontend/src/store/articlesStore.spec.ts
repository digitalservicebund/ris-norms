import { getArticlesByEli } from "@/services/articlesService"
import { createPinia, setActivePinia } from "pinia"
import { beforeEach, describe, expect, it, vi } from "vitest"
import { nextTick } from "vue"
import { useArticlesStore } from "./articlesStore"
import { Article } from "@/types/article"

vi.mock("@/services/articlesService", () => ({
  getArticlesByEli: vi.fn(),
}))

describe("useArticlesStore", () => {
  let mockArticles: Article[]

  beforeEach(() => {
    setActivePinia(createPinia())

    mockArticles = [
      {
        eid: "article eid 1",
        title: "article eid 1",
        enumeration: "1",
        affectedDocumentEli: "affected document eli 1",
        affectedDocumentZf0Eli: "affected document ZF0 eli 1",
      },
    ]

    vi.mocked(getArticlesByEli).mockResolvedValue(mockArticles)
  })

  it("loads articles correctly", async () => {
    const store = useArticlesStore()

    store.loadArticlesByEli("example/eli")
    await nextTick()

    expect(store.loading).toBe(true)
    expect(getArticlesByEli).toHaveBeenCalledOnce()

    await vi.waitUntil(() => !store.loading)

    expect(store.loading).toBe(false)
    expect(store.loadedArticles).toEqual(mockArticles)
  })
})
