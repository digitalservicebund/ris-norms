import { createTestingPinia } from "@pinia/testing"
import { defineStore } from "pinia"
import { beforeEach, describe, expect, test, vi } from "vitest"
import { nextTick, ref } from "vue"

describe("useArticles", () => {
  beforeEach(() => {
    vi.resetModules()
    vi.resetAllMocks()
  })

  test("should provide the articles", async () => {
    createTestingPinia({
      stubActions: false,
    })

    vi.doMock("@/store/articlesStore", () => ({
      useArticlesStore: vi.fn().mockReturnValue(
        defineStore("articles", () => {
          return {
            loadedArticles: ref([
              {
                eid: "article eid 1",
                title: "article eid 1",
                enumeration: "1",
                affectedDocumentEli: "example/eli",
              },
            ]),
            loadArticlesByEli: vi.fn(),
          }
        })(),
      ),
    }))

    const { useArticles } = await import("./useArticles")

    const identifier = ref<string>("")
    const articles = useArticles(identifier)

    expect(articles.value).toEqual([
      {
        eid: "article eid 1",
        title: "article eid 1",
        enumeration: "1",
        affectedDocumentEli: "example/eli",
      },
    ])
  })

  test("should load the articles when the ELI changes", async () => {
    const loadArticlesByEli = vi.fn()

    createTestingPinia({
      stubActions: false,
    })

    vi.doMock("@/store/articlesStore", () => ({
      useArticlesStore: vi.fn().mockReturnValue(
        defineStore("articles", () => {
          return {
            loadedArticles: ref(),
            loadArticlesByEli,
          }
        })(),
      ),
    }))

    const { useArticles } = await import("./useArticles")

    const eli = ref<string>("")
    useArticles(eli)

    eli.value = "1"
    await nextTick()

    eli.value = "1"
    await nextTick()

    expect(loadArticlesByEli).toBeCalledTimes(2)
  })
})
